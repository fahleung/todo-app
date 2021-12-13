package com.fahleung.demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User fabien = new User("fabien", "azerty123", "fabien@gmail.com");

            repository.save(fabien);
        };
    }
}
