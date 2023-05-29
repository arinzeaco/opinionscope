package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionsRepository;



    public List<Questions> getAllQuestions() {

        List<Questions> savedUser = questionsRepository.findAll();
        return savedUser;
    }

    public boolean addQuestions(Questions questions) {
        boolean isSaved = false;

        Questions savedQuestion = questionsRepository.save(questions);
        if (null != savedQuestion && savedQuestion.getQuestionId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }



}
