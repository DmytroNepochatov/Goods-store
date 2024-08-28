package com.hardcode.authserver.controller;

import com.hardcode.authserver.exception.UsernameNotFoundException;
import com.hardcode.authserver.model.RegisteredUser;
import com.hardcode.authserver.model.dto.AuthenticationRequestDTO;
import com.hardcode.authserver.service.TokenProviderService;
import com.hardcode.authserver.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthServerController {
    private final TokenProviderService tokenProviderService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            Optional<RegisteredUser> user = userDetailsService.loadUserByUsername(authenticationRequestDTO.getEmail());

            if(user.isPresent() && userDetailsService.isPasswordCorrectForUser(user.get(), authenticationRequestDTO.getPassword())) {
                Map<Object, Object> response = new HashMap<>();
                response.put("email", authenticationRequestDTO.getEmail());
                response.put("token", tokenProviderService.setToken(user.get()));

                return ResponseEntity.ok(response);
            }
            else {
                throw new UsernameNotFoundException("Invalid email/password combination");
            }
        }
        catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        tokenProviderService.deleteToken(token);
    }
}
