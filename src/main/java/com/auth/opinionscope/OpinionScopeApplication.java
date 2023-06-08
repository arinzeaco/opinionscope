package com.auth.opinionscope;

import com.auth.opinionscope.model.*;
import com.auth.opinionscope.model.auth.Country;
import com.auth.opinionscope.model.auth.User;
import com.auth.opinionscope.repository.OptionsRepository;
import com.auth.opinionscope.repository.QuestionRepository;
import com.auth.opinionscope.service.CountryService;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;

import static com.auth.opinionscope.model.Role.ADMIN;
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

    @Autowired
    OptionsRepository optionsRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CountryService countryService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(OpinionScopeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        adduser();
//        addManger();
//        addtag();
//        loadCountriesFromFile();
//        addQuestion();
    }

    public void addQuestion() {
//        Questions question = new Questions();
//        question.setQuestion("What is your favorite color?");
//        question.setAge(18);
//
//        Options option1 = new Options();
//        option1.setOptions_name("Red");
//        option1.setVotecount(0);
//        option1.setQuestion(question);
//        question.getOptions().add(option1);
//
//        questionRepository.save(question);

        Questions question = new Questions();
        question.setQuestion("What is your favorite color?");
        question.setAge(18);
        question.setTags(question.getTags());

        Options option1 = new Options();
        option1.setOptions_name("Red");
        option1.setOptions_count(0);

        // Save the option first to generate an ID
        optionsRepository.save(option1);

        // Add the option to the question's options property

        // Save the question
        questionRepository.save(question);
    }

    public void adduser() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("1234567890");
        user.setMobile_number("1234567890");
        user.setCountry("United States");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setDateOfBirth(Date.valueOf("1991-02-02"));
        user.setGoogleAuthId(null);
        user.setGoogleAuthSecret(null);
        user.setEmail_verified(false);
        user.setMobile_number_verified(false);
        user.setRole(USERS);
        userService.createUser(user);
    }

    public void addManger() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setPassword("1234567890");
        user.setMobile_number("1234567890");
        user.setCountry("United States");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setDateOfBirth(Date.valueOf("1991-02-02"));
        user.setGoogleAuthId(null);
        user.setGoogleAuthSecret(null);
        user.setEmail_verified(false);
        user.setMobile_number_verified(false);
        user.setRole(ADMIN);
        userService.createUser(user);
    }

    public void addtag() {
        Tags tags1 = new Tags();
        tags1.setTagName("FootBall");
        tagsService.saveTags(tags1);

        Tags tags2 = new Tags();
        tags2.setTagName("Nigeria");
        tagsService.saveTags(tags2);

        Tags tags3 = new Tags();
        tags3.setTagName("Music");
        tagsService.saveTags(tags3);

    }

    public void loadCountriesFromFile() {
        String filePath = "/Users/mac/Desktop/Java Project/opinion-scope/countries.csv"; // Replace with the actual file path
//        List<Country> countries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Assuming the CSV format is comma-separated

                Country country = new Country();
                country.setCountry_name(data[0]); // Assuming the country name is in the first column

//                countries.add(country);
                countryService.saveCountry(country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
