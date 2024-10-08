package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.Tags;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.TagsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/tags")
@RestController
public class TagsController {

    @Autowired
    TagsService tagsService;

    @GetMapping(value = "/get_tags")
    public ResponseEntity<Response> getQuestions() {
        var tags = tagsService.getTags();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Tags");
        response.setData(tags);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(value = "/add_tags")
    public ResponseEntity<Response> addQuestions(@Valid @RequestBody Tags tags) {

        var data = tagsService.saveTags(tags);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("User succesfully registered");
        response.setData(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
