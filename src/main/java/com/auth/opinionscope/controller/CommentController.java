package com.auth.opinionscope.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/questions")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommentController {

//    @Autowired
//    QuestionService questionsService;
//
//    @Autowired
//    OptionsService optionsService;
//å
//    //    @GetMapping(value = "/all_question/{userId}/{optionsListId}")
//
////    @PostMapping("/all_question")
//    @GetMapping("/all_question/{userId}")
//    public ResponseEntity<Response> getQuestions(@PathVariable Long userId) {
//        var questions =
//                questionsService.getAllQuestions(userId);
//        Response response = new Response();
//        response.setStatusCode("200");
//        response.setStatusMsg("Questions gotten successfully");
//        response.setData(questions);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(response);
//    }
//
//    @PostMapping("/add_questions")
//    public ResponseEntity<Response> addQuestions(@RequestBody Questions questions) {
//
//        var data = questionsService.addQuestions(questions);
//        Response response = new Response();
//        response.setStatusCode("200");
//        response.setStatusMsg("Question successfully registered");
//        response.setData(data);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(response);
//    }


}
