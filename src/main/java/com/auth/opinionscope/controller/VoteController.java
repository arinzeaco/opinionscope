package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.Tags;
import com.auth.opinionscope.model.VoteCount;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.TagsService;
import com.auth.opinionscope.service.VoteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/votes")
@RestController
public class VoteController {

    @Autowired
    VoteService voteService;


    @PostMapping(value = "/add_vote")
    public ResponseEntity<Response> addVote(@Valid @RequestBody VoteCount voteCount) {
        var data = voteService.saveVote(voteCount);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Votes added succesfully");
        response.setData(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping(value = "/get_vote")
    public ResponseEntity<Response> getVote() {

//        var data = voteService.getVotes();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Vote fetched succesfully");
//        response.setData(data);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(response);
    }


}
