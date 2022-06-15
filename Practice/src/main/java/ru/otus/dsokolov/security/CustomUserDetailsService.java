package ru.otus.dsokolov.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.otus.dsokolov.domain.User;
import ru.otus.dsokolov.repository.UserRepository;

import java.text.MessageFormat;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException(MessageFormat.format("Unknown user: ", username)));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .disabled(!Boolean.parseBoolean(user.getEnabled()))
                .roles(user.getRole())
                .build();
        return userDetails;
    }

}
