package com.hardcode.apigateway.service;

import com.hardcode.apigateway.model.Token;
import com.hardcode.apigateway.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final CacheManager cacheManager;
    private final TokenRepository tokenRepository;

    public void saveToken(String token) {
        CompletableFuture.runAsync(() -> {
            Cache cache = cacheManager.getCache("tokenStorage");
            if (cache != null) {
                cache.put(token, token);
            }
        });

        CompletableFuture.runAsync(() -> tokenRepository.save(new Token(0L, token)));
    }

    public void deleteToken(String token) {
        CompletableFuture.runAsync(() -> {
            Cache cache = cacheManager.getCache("tokenStorage");
            if (cache != null) {
                cache.evict(token);
            }
        });

        CompletableFuture.runAsync(() -> tokenRepository.deleteToken(token));
    }

    @Cacheable(value = "tokenStorage", key = "#token", unless = "#result.isEmpty()")
    public String findToken(String token) {
        Optional<Token> tokenObject = tokenRepository.findFirstByToken(token);

        return tokenObject.map(Token::getToken).orElse("");
    }
}
