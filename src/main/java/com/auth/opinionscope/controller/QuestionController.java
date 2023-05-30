package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.Country;
import com.auth.opinionscope.model.OptionsList;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.model.TagList;
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

    @GetMapping(value = "/all")
    public ResponseEntity<Response> getQuestions() {
        var questions = questionsService.getAllQuestions();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("All succesfully registered");
        response.setData(questions);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(value = "/add_questions")
    public ResponseEntity<Response> addQuestions(@Valid @RequestBody PostDTO postDTO) {
        Questions questions = new Questions();
        questions.setQuestion(postDTO.getTitle());
        questions.setAge(4);

        Set<TagList> tags = new HashSet<>();
        for (String tagContent : postDTO.getTagContents()) {
            TagList tag = new TagList();
            tag.setName(tagContent);
            tags.add(tag); // Save the tag to generate its ID

        }
        Set<OptionsList> options = new HashSet<>();
        for (String optionsContent : postDTO.getOptionContents()) {
            OptionsList option = new OptionsList();
            option.setOptions_name(optionsContent);
            option.setVotecount(0);
            options.add(option);
//            Logoption.getOptionsListId()
            // Save the tag to generate its ID
        }

//        Set<Country> countries = new HashSet<>();
//        for (String optionsContent : postDTO.getCountryContents()) {
//            Country country = new Country();
//            country.setCountry_name(optionsContent);
//            countries.add(country);
////            Logoption.getOptionsListId()
//            // Save the tag to generate its ID
//        }

//        questions.setCountry(countries);
        questions.setTagList(tags);
        questions.setOptionsList(options);

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
