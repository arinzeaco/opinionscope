package com.auth.opinionscope.service;

import com.auth.opinionscope.dataModel.OptionDetails;
import com.auth.opinionscope.dataModel.QuestionsModel;
import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.OptionsRepository;
import com.auth.opinionscope.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


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

    public List<QuestionsModel> getAllQuestions(@RequestParam("userId") Long userId) {

        List<Questions> quest = questionsRepository.findAll();

        List<QuestionsModel> questionsModels = new ArrayList<>();

        for (Questions question : quest) {

            QuestionsModel questionsModel = new QuestionsModel();

            questionsModel.setQuestionId(question.getQuestionId());
            questionsModel.setQuestion(question.getQuestion());
            questionsModel.setAge(question.getAge());
            questionsModel.setTags(question.getTags());

            questionsModel.setCountry(question.getCountry());

            // Get the options for the question
            // Get the country for the question

            Set<OptionDetails> options = new HashSet<>();
            for (Long optionId : question.getOptionsIDs()) {
                OptionDetails optionDetails = new OptionDetails();
                Options findByOptionsListId = optionsRepository.findByOptionsId(optionId);
//                ee.setOptions_name();
                optionDetails.setOptionsListId(findByOptionsListId.getOptionsId());
                optionDetails.setOptions_name(findByOptionsListId.getOptions_name());
                optionDetails.setVotecount(findByOptionsListId.getOptions_count());
                optionDetails.setUserVoted(voteService.getUserIdAndQuestionIdAndOptionsId(userId,question.getQuestionId(),
                        findByOptionsListId.getOptionsId()    ));


                options.add(optionDetails);
            }
            questionsModel.setOptions(options);

            questionsModels.add(questionsModel);
        }
       return questionsModels;
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
            opt.setOptions_count(0);
//            opt.setUserVoted(false);
            options.add(opt);

            Options savedOptions = optionsRepository.save(opt);
            if (savedOptions.getOptionsId() > 0) {
                optionsID.add(savedOptions.getOptionsId());
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

//    public boolean saveOptions(Options options) {
//        boolean isSaved = false;
//
//        Options savedOptions = optionsRepository.save(options);
//        if (savedOptions.getOptionsId() > 0) {
//            isSaved = true;
//        }
//        return isSaved;
//    }
}
