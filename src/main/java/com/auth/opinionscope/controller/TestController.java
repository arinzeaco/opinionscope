package com.auth.opinionscope.controller;

import com.auth.opinionscope.rest.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/test")
public class TestController {

    @GetMapping("/adds")
    public ResponseEntity<Response> addQuest() {
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Question succesfullyyyyy registered");
        response.setData("data");
        return ResponseEntity.ok().body(response);


    }
}
