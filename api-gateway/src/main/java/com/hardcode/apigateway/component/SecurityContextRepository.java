package com.hardcode.apigateway.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Value("${jwt.header}")
    private String authorizationHeader;

    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        Mono<String> stringMono = Mono.justOrEmpty(serverWebExchange.getRequest().getHeaders().getFirst(authorizationHeader));
        return stringMono.flatMap(this::getSecurityContext);
    }

    private Mono<? extends SecurityContext> getSecurityContext(String token) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
        return authenticationManager.authenticate(authentication).map(SecurityContextImpl::new)
                .onErrorResume(ex -> Mono.empty());
    }
}
