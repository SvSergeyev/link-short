package tech.sergeyev.linkshort.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.sergeyev.linkshort.dto.ShortLinkDto;
import tech.sergeyev.linkshort.service.ShortLinkService;

import java.net.URI;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> create(@RequestBody ShortLinkDto dto) {
        var tokenizedUrl = shortLinkService.createAndGetTokenizedUrl(dto);
        return ResponseEntity.ok(tokenizedUrl);
    }

    @GetMapping("/redirect/{token}")
    public ResponseEntity<String> getByToken(@PathVariable("token") String token) {
        try {
            var url = shortLinkService.getUrlByToken(token);
            var headers = new HttpHeaders();
            headers.setLocation(URI.create(url));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
