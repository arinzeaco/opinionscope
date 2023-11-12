package com.auth.opinionscope.dataModel;

import com.auth.opinionscope.model.Options;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Data
public class QuestionsModel {

    private long questionId;

    private String question;

    private int age;

    private int likeCount;

    private boolean isLiked;

    private Set<String> tags = new HashSet<>();

    private Set<String> country = new HashSet<>();

    private Set<OptionDetails> options = new HashSet<>();


}

