package com.auth.opinionscope.controller;

import com.auth.opinionscope.dataModel.LikedQuestionsModel;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.OptionsService;
import com.auth.opinionscope.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/questions")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionsService;

    @Autowired
    OptionsService optionsService;

    //    @GetMapping(value = "/all_question/{userId}/{optionsListId}")

    //    @PostMapping("/all_question")
    @GetMapping("/all_question/{userId}")
    public ResponseEntity<Response> getQuestions(@PathVariable Long userId) {
        var questions =
                questionsService.getAllQuestions(userId);
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

        var questionAdded = questionsService.addQuestions(questions);

        Response response = new Response();

        if (questionAdded) {
            response.setStatusCode("200");
            response.setStatusMsg("Question successfully registered");
            response.setData(questionAdded);
        } else {
            response.setStatusCode("400");
            response.setStatusMsg("Failed to Add question");
            response.setData(questionAdded);

        }


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @PostMapping("/liked_question")
    public ResponseEntity<Response> addLikedQuestions(@RequestBody LikedQuestionsModel likedQuestionsModel) {

        var likeQuestion = questionsService.saveLikedQuestion(likedQuestionsModel);

        Response response = new Response();

        if (likeQuestion) {
            response.setStatusCode("200");
            response.setStatusMsg("Question successfully Liked");
            response.setData(likeQuestion);
        } else {
            response.setStatusCode("400");
            response.setStatusMsg("Failed to Add question");
            response.setData(likeQuestion);

        }


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/delete_liked_question")
    public ResponseEntity<Response> deleteLikedQuestion(@RequestBody LikedQuestionsModel likedQuestionsModel) {

        var deleteLikedQuestion = questionsService.deleteLikedQuestion(likedQuestionsModel);
        Response response = new Response();

        if (deleteLikedQuestion) {
            response.setStatusCode("200");
            response.setStatusMsg("Question successfully Liked");
            response.setData(deleteLikedQuestion);
        } else {
            response.setStatusCode("400");
            response.setStatusMsg("Failed to Add question");
            response.setData(deleteLikedQuestion);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/get_liked/{userId}")
    public ResponseEntity<Response> selectLiked(@PathVariable Long userId) {
        var questions =
                questionsService.getAllLikedQuestions(userId);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question liked successfully");
        response.setData(questions);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
