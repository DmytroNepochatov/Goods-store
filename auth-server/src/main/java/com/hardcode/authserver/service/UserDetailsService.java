package com.hardcode.authserver.service;

import com.hardcode.authserver.exception.UsernameNotFoundException;
import com.hardcode.authserver.model.RegisteredUser;
import com.hardcode.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<RegisteredUser> loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findFirstByEmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
    }

    public boolean isPasswordCorrectForUser(RegisteredUser user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
