package com.auth.opinionscope.dataModel.request;

import lombok.Data;

@Data
public class AllQuestionRequest {
    Long userId;
    Long optionsListId;
}
