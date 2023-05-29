package com.auth.opinionscope;

import com.auth.opinionscope.model.*;
import com.auth.opinionscope.repository.OptionsListRepository;
import com.auth.opinionscope.repository.QuestionRepository;
import com.auth.opinionscope.repository.TagsRepository;
import com.auth.opinionscope.service.QuestionService;
import com.auth.opinionscope.service.TagsService;
import com.auth.opinionscope.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static com.auth.opinionscope.model.Role.USERS;

@EnableJpaRepositories("com.auth.opinionscope.repository")
@EntityScan("com.auth.opinionscope.model")
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class OpinionScopeApplication implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    OptionsListRepository optionsListRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(OpinionScopeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User johnDoe = new User();
        johnDoe.setEmail("johndoe@example.com");
        johnDoe.setPassword("1234567890");
        johnDoe.setMobile_number("1234567890");
        johnDoe.setCountry("United States");
        johnDoe.setFirstname("John");
        johnDoe.setLastname("Doe");
        johnDoe.setDateOfBirth(Date.valueOf("1991-02-02"));
        johnDoe.setGoogleAuthId(null);
        johnDoe.setGoogleAuthSecret(null);
        johnDoe.setEmail_verified(false);
        johnDoe.setMobile_number_verified(false);
        johnDoe.setRole(USERS);
        userService.createUser(johnDoe);

    }
}
