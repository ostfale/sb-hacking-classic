package de.ostfale.book.sbhackingclassic.configuration;

import de.ostfale.book.sbhackingclassic.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> repository.findByName(username)
                .map(user -> User.withDefaultPasswordEncoder()
                        .username(user.getName())
                        .password(user.getPassword())
                        .authorities(user.getRoles().toArray(new String[0]))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Could not find: " + username));

    }

    @Bean
    public CommandLineRunner userLoader(UserRepository repository) {
        return args -> repository.save(createDefaultUser());
    }

    private de.ostfale.book.sbhackingclassic.model.User createDefaultUser() {
        return new de.ostfale.book.sbhackingclassic.model.User("louis", "test", List.of("ROLE_USER"));
    }
}
