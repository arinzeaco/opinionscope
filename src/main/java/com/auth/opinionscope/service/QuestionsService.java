//package com.auth.opinionscope.service;
//
//import com.auth.opinionscope.config.AuthenticationRequest;
//import com.auth.opinionscope.config.AuthenticationResponse;
//import com.auth.opinionscope.config.JwtService;
//import com.auth.opinionscope.model.Questions;
//import com.auth.opinionscope.model.User;
//import com.auth.opinionscope.model.token.Token;
//import com.auth.opinionscope.model.token.TokenType;
//import com.auth.opinionscope.repository.QuestionsRepository;
//import com.auth.opinionscope.repository.TokenRepository;
//import com.auth.opinionscope.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Random;
//
///*
//@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
//Logger static property in the class at compilation time.
//* */
//
//@Service
//public class QuestionsService {
//
//    @Autowired
//    private QuestionsRepository questionsRepository;
//
//
//
//    public List<Questions> getAllQuestions() {
//
//        List<Questions> savedUser = questionsRepository.findAll();
//        return savedUser;
//    }
//
//
//}
