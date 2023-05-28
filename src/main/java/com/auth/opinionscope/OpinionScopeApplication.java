package com.auth.opinionscope;

import com.auth.opinionscope.model.*;
import com.auth.opinionscope.repository.UserRepository;
import com.auth.opinionscope.service.OptionsListService;
import com.auth.opinionscope.service.QuestionsService;
import com.auth.opinionscope.service.TagsService;
import com.auth.opinionscope.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

import static com.auth.opinionscope.model.Role.USERS;

@EnableJpaRepositories("com.auth.opinionscope.repository")
@EntityScan("com.auth.opinionscope.model")
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class OpinionScopeApplication implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    TagsService tagsService;

//    @Autowired
//    OptionsListService optionsListService;

    @Autowired
    QuestionsService questionsService;

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

        // Create and save OptionsList
        OptionsList optionsList = new OptionsList();
        optionsList.setName("Option 1");
//        optionsListService.saveOptions(optionsList);

        // Create and save TagList
        TagList tagList = new TagList();
        tagList.setName("Tag 1");
        tagsService.saveTags(tagList);

        // Retrieve managed entities
        TagList managedTagList = tagsService.getTagListById();
//        OptionsList managedOptionsList = optionsListService.getOptionsListById();

        // Create and save Questions
        Questions question = new Questions();
        question.setQuestion("What is your favorite color?");
        question.setAge(20);
//        question.setOptions(managedOptionsList);
        question.getTagList().add(managedTagList);
        question.getCountry().add(new Country(/* country data */));
        questionsService.addQuestions(question);

        Questions question2 = new Questions();
        question2.setQuestion("What is your favorite music genre?");
        question2.setAge(21);
//        question2.setOptions(managedOptionsList);
        question2.getTagList().add(managedTagList);
        question2.getCountry().add(new Country(/* country data */));
        questionsService.addQuestions(question2);

        Questions question3 = new Questions();
        question3.setQuestion("What is your favorite country?");
        question3.setAge(22);
//        question2.setOptions(managedOptionsList);
        question2.getTagList().add(managedTagList);
        question3.getCountry().add(new Country(/* country data */));
        questionsService.addQuestions(question3);

    }
}
