package com.hardcode.authserver.service;

import com.hardcode.authserver.component.JwtTokenProvider;
import com.hardcode.authserver.model.RegisteredUser;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TokenProviderService {
    private final JwtTokenProvider jwtTokenProvider;
    private final Tracer tracer;
    private final RestTemplate restTemplate;

    public String setToken(RegisteredUser registeredUser) {
        String token = jwtTokenProvider.createToken(registeredUser.getEmailAddress(), registeredUser.getRole().name());

        makeRequest(token, "lb://api-gateway/tokens/save");
        return token;
    }

    public void deleteToken(String token) {
        makeRequest(token, "lb://api-gateway/tokens/delete");
    }

    private void makeRequest(String token, String url) {
        Span setTokenLookup = tracer.nextSpan().name("SetTokenLookup");

        try (Tracer.SpanInScope spanInScope = tracer.withSpan(setTokenLookup.start())) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            HttpEntity<String> requestEntity = new HttpEntity<>(token, headers);

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);
        }
        finally {
            setTokenLookup.end();
        }
    }
}
