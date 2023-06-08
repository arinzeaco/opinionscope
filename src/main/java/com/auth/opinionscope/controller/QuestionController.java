package com.auth.opinionscope.controller;

import com.auth.opinionscope.dataModel.request.AllQuestionRequest;
import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.OptionsService;
import com.auth.opinionscope.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@RequestMapping("api/v1/questions")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionsService;

    @Autowired
    OptionsService optionsService;

    //    @GetMapping(value = "/all_question/{userId}/{optionsListId}")
    @PostMapping("/all_question")
    public ResponseEntity<Response> getQuestions(@RequestBody AllQuestionRequest allQuestionRequest) {
        var questions =
                questionsService.getAllQuestions(allQuestionRequest.getUserId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Questions gotten successfully");
        response.setData(questions);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/add_questions")
    public ResponseEntity<Response> addQuestions(@RequestBody Questions questions) {
//        Questions questions = new Questions();
//        questions.setQuestion(postDTO.getTitle());
//        questions.setAge(18);
//        questions.setTags(postDTO.getTagContents());
//        questions.setCountry(postDTO.getCountryContents());

//        Set<Options> options = new HashSet<>();
//        for (String optionsContent : postDTO.getOptionContents()) {
//            Options option = new Options();
//            option.setOptions_name(optionsContent);
//            option.setVotecount(0);
//            options.add(option);
//        }

//        Set<Options> options = new HashSet<>();
//        for (String option : questions.getOptions()) {
//            Options opt = new Options();
//            opt.setOptions_name(option);
//            opt.setVotecount(0);
//            opt.setUserVoted(false);
//            options.add(opt);
//            optionsService.saveOptions(opt);
//        }
//        questions.setOptions(questions.getOptions());
//        questions.setOptions(options);

        var data = questionsService.addQuestions(questions);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question successfully registered");
        response.setData(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/addss")
    public ResponseEntity<Response> addQuest() {
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question succesfullyyyyy registered");
        response.setData("data");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
