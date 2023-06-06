package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.OptionsRepository;
import com.auth.opinionscope.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionsRepository;

    @Autowired
    OptionsService optionsService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private OptionsRepository optionsRepository;

    public List<Questions> getAllQuestions() {
//        op.setUserVoted(voteService.getUserIdAndOptionsListId(userId,OptionsListId));

        List<Questions> savedUser = questionsRepository.findAll();
//        for (String op : savedUser.get(0).getOptions()) {
//            long count = voteService.getUserIdAndOptionsListId(savedUser.get(0).getQuestionId());
//            op.setVotecount((int) count);
//        }
        return savedUser;
    }

    public boolean addQuestions(Questions postDTO) {
        Questions questions = new Questions();
        questions.setQuestion(postDTO.getQuestion());
        questions.setAge(18);
        questions.setTags(postDTO.getTags());
        questions.setCountry(postDTO.getCountry());
        questions.setOptions(postDTO.getOptions());

        Set<Options> options = new HashSet<>();
        Set<Long> optionsID = new HashSet<>();
        for (String option : postDTO.getOptions()) {
            Options opt = new Options();
            opt.setOptions_name(option);
            opt.setVotecount(0);
            opt.setUserVoted(false);
            options.add(opt);

            Options savedOptions = optionsRepository.save(opt);
            if (savedOptions.getOptionsListId() > 0) {
                optionsID.add(savedOptions.getOptionsListId());
            }

        }
        questions.setOptionsIDs(optionsID);


        boolean isSaved = false;

        Questions savedQuestion = questionsRepository.save(questions);
        if (savedQuestion.getQuestionId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public boolean saveOptions(Options options) {
        boolean isSaved = false;

        Options savedOptions = optionsRepository.save(options);
        if (savedOptions.getOptionsListId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }
}
