package com.hardcode.apigateway.component;

import com.hardcode.apigateway.exception.JwtAuthenticationException;
import com.hardcode.apigateway.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Lazy
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        try {
            return Mono.just(tokenValidate(token));
        }
        catch (JwtAuthenticationException e) {
            return Mono.error(new JwtAuthenticationException(e.getMessage()));
        }
    }

    private UsernamePasswordAuthenticationToken tokenValidate(String token) {
        String tokenFromDB = tokenService.findToken(token);

        try {
            if (!tokenFromDB.isEmpty() && jwtTokenProvider.validateToken(token)) {
                return jwtTokenProvider.getAuthentication(token);
            }
            else {
                throw new JwtAuthenticationException("JWT token is expired or invalid");
            }
        }
        catch (JwtAuthenticationException e) {
            throw new JwtAuthenticationException(e.getMessage());
        }
    }
}
