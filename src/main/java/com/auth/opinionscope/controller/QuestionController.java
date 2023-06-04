package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.rest.Response;
//import com.auth.opinionscope.service.QuestionsService;
import com.auth.opinionscope.service.QuestionService;
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



    @GetMapping(value = "/all/{userId}/{optionsListId}")
    public ResponseEntity<Response> getQuestions(@PathVariable("userId") long userId, @PathVariable("optionsListId") long optionsListId) {
        var questions = questionsService.getAllQuestions(userId,optionsListId);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Questions Gotten Successfully");
        response.setData(questions);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/add_questions")
    public Response addQuestions(@RequestBody Questions questions) {
//        Questions question = new Questions();
//        question.setQuestion(questions.getQuestion());
//        question.setAge(questions.getAge());
//        question.setTags(questions.getTags());
//        question.setCountry(questions.getCountry());
//
//        Set<Options> options = new HashSet<>();
////        Options option = new Options();
////        option.setOptions_name(questions.getOptions());
////        option.setVotecount(0);
////        options.add(option);
////        options.add("dd")
//        for (String optionsContent : questions.getOptions()) {
//            Options option = new Options();
//            option.setOptions_name(optionsContent);
//            option.setVotecount(0);
//            options.add(option);
//        }
        questions.setOptions(null);

        var data = questionsService.addQuestions(questions);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question succesfully registered");
        response.setData(true);

        return response;
    }


}
