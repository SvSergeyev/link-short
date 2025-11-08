package tech.sergeyev.linkshort.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.sergeyev.linkshort.dto.ShortLinkDto;
import tech.sergeyev.linkshort.model.ShortLink;
import tech.sergeyev.linkshort.repository.ShortLinkRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ShortLinkService {
    @Value("${links.baseUrl}")
    private String baseUrl;
    @Value("${token.maxAttempts}")
    private int maxAttempts = 10;
    @Value("${token.length}")
    private int tokenLength = 9;
    @Value("${links.expirationDays}")
    private int expirationDays = 30;

    private final ShortLinkRepository shortLinkRepository;

    public String createAndGetTokenizedUrl(ShortLinkDto dto) {
        var url = dto.url();
        var created = shortLinkRepository.getShortLinkByUrl(url);
        if (created == null) {
            var token = getUniqueToken();
            created = new ShortLink(url, token);
            shortLinkRepository.save(created);
        }
        return tokenizeUrl(created);
    }

    public String getUrlByToken(String token) {
        var link = shortLinkRepository.getShortLinkByToken(token);
        if (link == null) {
            throw new NoSuchElementException("Link not found");
        }
        return link.getUrl();
    }

    public void removeObsoleteShortLinks() {
        var expirationDate = LocalDateTime.now().minusDays(expirationDays);
        var obsoleteLinks = shortLinkRepository.findAllObsoleteShortLinks(expirationDate);
        shortLinkRepository.deleteAllById(obsoleteLinks.stream().map(ShortLink::getId).toList());
    }

    private String getUniqueToken() {
        for (int i = 0; i < maxAttempts; i++) {
            var token = TokenGenerator.generateToken(tokenLength);
            if (!shortLinkRepository.existsShortLinkByToken(token)) {
                return token;
            }
        }
        throw new IllegalStateException("Cannot generate unique token");
    }

    private String tokenizeUrl(ShortLink link) {
        return baseUrl + link.getToken();
    }
}
