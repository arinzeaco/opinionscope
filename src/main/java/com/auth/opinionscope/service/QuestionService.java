package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionsRepository;

    @Autowired
    private VoteService voteService;

    public List<Questions> getAllQuestions(@RequestParam("userId") long userId,
                                           @RequestParam("optionsListId") long ptionsListId) {
//        op.setUserVoted(voteService.getUserIdAndOptionsListId(userId,OptionsListId));

        List<Questions> savedUser = questionsRepository.findAll();
        for (Options op : savedUser.get(0).getOptions()) {
            long count = voteService.getVoteById(op.getOptionsListId());
            op.setVotecount((int) count);
        }
        return savedUser;
    }

    public boolean addQuestions(Questions questions) {
        boolean isSaved = false;

        Questions savedQuestion = questionsRepository.save(questions);
        if (savedQuestion.getQuestionId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }


}
