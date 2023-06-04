package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.Tags;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.CountryService;
import com.auth.opinionscope.service.TagsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/country")
@RestController
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping(value = "/get_countries")
    public ResponseEntity<Response> getQuestions() {
        var tags = countryService.getCountry();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Tags");
        response.setData(tags);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
