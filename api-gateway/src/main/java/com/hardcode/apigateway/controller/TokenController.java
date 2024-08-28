package com.hardcode.apigateway.controller;

import com.hardcode.apigateway.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/save")
    public void saveToken(@RequestBody String token) {
        tokenService.saveToken(token);
    }

    @PostMapping("/delete")
    public void deleteToken(@RequestBody String token) {
        tokenService.deleteToken(token);
    }
}
