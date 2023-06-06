package com.auth.opinionscope.dataModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
public class PostDto {

    private String title;

    private String content;

    private Set<String> tagContents;

    private Set<String> options;

    private Set<String> countryContents;

}
