//package com.auth.opinionscope.controller;
//
//import com.auth.opinionscope.model.User;
//import com.auth.opinionscope.rest.Response;
////import com.auth.opinionscope.service.QuestionsService;
//import com.auth.opinionscope.service.UserService;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@Slf4j
//@RequestMapping("api/v1/questions")
//@RestController
//public class QuestionController {
//
//    @Autowired
//    QuestionsService questionsService;
//
//
//
//
//    @GetMapping(value = "/")
//    public ResponseEntity<Response> getQuestions() {
//
//        var token = questionsService.getAllQuestions();
//        Response response = new Response();
//        response.setStatusCode("200");
//        response.setStatusMsg("User succesfully registered");
//        response.setData(token);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(response);
//
//    }
//
//
//
//
//}
