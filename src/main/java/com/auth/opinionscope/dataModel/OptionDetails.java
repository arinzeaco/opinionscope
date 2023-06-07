package com.auth.opinionscope.dataModel;

import lombok.Data;

@Data
public class OptionDetails {

    private Long optionsListId;

    private String options_name;

    private int votecount;

    boolean userVoted;


}