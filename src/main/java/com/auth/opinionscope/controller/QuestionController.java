package com.auth.opinionscope.controller;

import com.auth.opinionscope.dataModel.PostDto;
import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.QuestionRepository;
import com.auth.opinionscope.rest.Response;
//import com.auth.opinionscope.service.QuestionsService;
import com.auth.opinionscope.service.QuestionService;
import jakarta.validation.Valid;
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
    private QuestionRepository questionRepository;

    @GetMapping(value = "/all/{userId}/{optionsListId}")
    public ResponseEntity<Response> getQuestions(@PathVariable("userId") long userId,@PathVariable("optionsListId") long optionsListId) {
        var questions = questionsService.getAllQuestions(userId,optionsListId);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Questions Gotten Successfully");
        response.setData(questions);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(value = "/add_questions")
    public ResponseEntity<Response> addQuestions(@Valid @RequestBody PostDto postDTO) {
        Questions questions = new Questions();
        questions.setQuestion(postDTO.getTitle());
        questions.setAge(18);
        questions.setTags(postDTO.getTagContents());
        questions.setCountry(postDTO.getCountryContents());

        Set<Options> options = new HashSet<>();
        for (String optionsContent : postDTO.getOptionContents()) {
            Options option = new Options();
            option.setOptions_name(optionsContent);
            option.setVotecount(0);
            options.add(option);
        }
        questions.setOptions(options);

        var data = questionRepository.save(questions);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question succesfully registered");
        response.setData(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
