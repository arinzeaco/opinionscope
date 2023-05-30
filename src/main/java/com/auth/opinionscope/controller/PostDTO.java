package com.auth.opinionscope.controller;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private String title;

    private String content;

    private Set<String> tagContents;

    private Set<String> optionContents;

    private Set<String> countryContents;

    // Constructors, getters, and setters

    // Rest of the class
}
