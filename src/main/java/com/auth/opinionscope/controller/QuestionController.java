package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.rest.Response;
//import com.auth.opinionscope.service.QuestionsService;
import com.auth.opinionscope.service.QuestionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/questions")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionsService;

    @GetMapping(value = "/all")
    public ResponseEntity<Response> getQuestions() {
        var token = questionsService.getAllQuestions();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("User succesfully registered");
        response.setData(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(value = "/add_questions")
    public ResponseEntity<Response> addQuestions(@Valid @RequestBody Questions questions) {

        var data = questionsService.addQuestions(questions);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question succesfully registered");
        response.setData(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
