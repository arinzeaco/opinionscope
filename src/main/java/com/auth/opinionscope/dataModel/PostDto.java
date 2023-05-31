package com.auth.opinionscope.dataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

    private String title;

    private String content;

    private Set<String> tagContents;

    private Set<String> optionContents;

    private Set<String> countryContents;

}
