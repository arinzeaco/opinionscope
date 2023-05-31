package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.OptionsListRepository;
import com.auth.opinionscope.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionsRepository;

    @Autowired
    private VoteService voteService;


    public List<Questions> getAllQuestions() {

        List<Questions> savedUser = questionsRepository.findAll();
        for (Options op : savedUser.get(0).getOptions()) {
            log.info(" op.getOptions_name()");
            long count = voteService.getVoteById(op.getOptionsListId());
            op.setVotecount((int) count);
        }
//        optionsListRepository.findAllById()savedUser.get(0).
        return savedUser;
    }

    public boolean addQuestions(Questions questions) {
        boolean isSaved = false;

        Questions savedQuestion = questionsRepository.save(questions);
        if (null != savedQuestion && savedQuestion.getQuestionId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }


}
