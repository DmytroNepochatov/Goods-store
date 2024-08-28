package com.hardcode.apigateway.config;

import com.hardcode.apigateway.component.AuthenticationEntryPoint;
import com.hardcode.apigateway.component.AuthenticationManager;
import com.hardcode.apigateway.component.SecurityContextRepository;
import com.hardcode.apigateway.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeExchange()
                .pathMatchers("/eureka/web").hasAuthority(Role.ADMIN.name())
                .pathMatchers("/tokens/**").permitAll()
                .pathMatchers("/auth/login").permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .build();
    }
}
