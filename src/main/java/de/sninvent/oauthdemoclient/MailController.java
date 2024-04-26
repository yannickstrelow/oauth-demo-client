package de.sninvent.oauthdemoclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController("/api")
public class MailController {

    @GetMapping("/mails")
    public ResponseEntity<List<Mail>> getMails(@RequestHeader String userToken) {
        var webClient = WebClient.builder().baseUrl("http://localhost:8082/api").defaultHeader("Authorization", userToken).build();

        var uriSpec = webClient.get();

        var bodySpec = uriSpec.uri("/mails");
        bodySpec.header("Authorization", userToken);
        var mailList = bodySpec.exchangeToMono(response -> response.bodyToMono(List.class));

        return ResponseEntity.ok(mailList.block());
    }
}
