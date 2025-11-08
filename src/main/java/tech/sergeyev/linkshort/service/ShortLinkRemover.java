package tech.sergeyev.linkshort.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortLinkRemover {
    private final ShortLinkService shortLinkService;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeObsoleteShortLinks() {
        shortLinkService.removeObsoleteShortLinks();
    }
}
