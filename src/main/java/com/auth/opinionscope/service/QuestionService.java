package com.auth.opinionscope.service;

import com.auth.opinionscope.dataModel.LikedQuestionsModel;
import com.auth.opinionscope.dataModel.OptionDetails;
import com.auth.opinionscope.dataModel.QuestionsModel;
import com.auth.opinionscope.model.LikedQuestions;
import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.LikedQuestionsRepository;
import com.auth.opinionscope.repository.OptionsRepository;
import com.auth.opinionscope.repository.QuestionRepository;
import com.auth.opinionscope.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    OptionsService optionsService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private LikedQuestionsRepository likedQuestionsRepository;

    public List<QuestionsModel> getAllQuestions(@RequestParam("userId") Long userId) {

        List<Questions> quest = questionsRepository.findAll();

        List<QuestionsModel> questionsModels = new ArrayList<>();

        for (Questions question : quest) {

            QuestionsModel questionsModel = new QuestionsModel();

            questionsModel.setQuestionId(question.getQuestionId());
            questionsModel.setQuestion(question.getQuestion());
            questionsModel.setAge(question.getAge());
            questionsModel.setLikeCount(question.getLikeCount());
            questionsModel.setTags(question.getTags());
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
                optionDetails.setUserVoted(voteService.getUserIdAndQuestionIdAndOptionsId(userId, question.getQuestionId(),
                        findByOptionsListId.getOptionsId()));


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
        questions.setLikeCount(0);
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
    ;


    public boolean saveLikedQuestion(LikedQuestionsModel likedQuestionsModel) {
        Optional<Questions> question = questionsRepository.findByQuestionId(likedQuestionsModel.getQuestionId());
        Optional<UserData> userData = userRepository.findByUserId(likedQuestionsModel.getUserId());

        // Check if the question and user data exist
        if (question.isPresent() && userData.isPresent()) {

            // Check if the user has already liked the question
            if (!likedQuestionsRepository.existsByLikeQuestionsAndUserDataLiked(question.get(), userData.get())) {

                // Increment the likeCount
                question.get().setLikeCount(question.get().getLikeCount() + 1);
                questionsRepository.save(question.get());

                // Save the liked question entry
                LikedQuestions likedQuestions = new LikedQuestions();
                likedQuestions.setLikeQuestions(question.get());
                likedQuestions.setUserDataLiked(userData.get());

                LikedQuestions savedOptions = likedQuestionsRepository.save(likedQuestions);

                return savedOptions.getLikeQuestionId() > 0; // Return true if saved successfully
            }
        }

        return false; // Question or user data not found, or user has already liked the question
    }


    public boolean deleteLikedQuestion(LikedQuestionsModel likedQuestionsModel) {
        Optional<Questions> question = questionsRepository.findByQuestionId(likedQuestionsModel.getQuestionId());
        Optional<UserData> userData = userRepository.findByUserId(likedQuestionsModel.getUserId());

        // Check if the question and user data exist
        if (question.isPresent() && userData.isPresent()) {

            // Decrement the likeCount
            if (question.get().getLikeCount() > 0) {
                question.get().setLikeCount(question.get().getLikeCount() - 1);
                questionsRepository.save(question.get());
            }

            // Delete the liked question entry
//            likedQuestionsRepository.deleteByLikeQuestionsAndUserDataLiked(question.get(), userData.get());
//            likedQuestionsRepository.deleteByQuestionIdAndUserId(question.get(),userData.get());
            likedQuestionsRepository.deleteByQuestionsAndUserDataLiked(likedQuestionsModel.getQuestionId(),likedQuestionsModel.getUserId());

            return true; // Deletion successful
        }

        return false; // Question or user data not found
    }

    public List<Questions> getAllLikedQuestions(Long userId) {
        List<Questions> likedQuestions = likedQuestionsRepository.findLikedQuestionsByUserId(userId);

//        List<Long> likedQuestionIds = likedQuestions.stream()
//                .map(likedQuestion -> likedQuestion.getLikeQuestions().getQuestionId())
//                .collect(Collectors.toList());
        System.out.print(likedQuestions);
        return likedQuestions;
//        return questionsRepository.findAllById(likedQuestionIds);
    }

//    public List<QuestionsModel> getAllLikedQuestions(Long userId) {
//
//        List<LikedQuestions> likedQuestions = likedQuestionsRepository.findByUserId(userId);
//
//        List<QuestionsModel> questionsModels = new ArrayList<>();
//
//        for (LikedQuestions likedQuestion : likedQuestions) {
//            QuestionsModel questionsModel = new QuestionsModel();
//
//            // Assuming LikedQuestions has a reference to Questions
//            Questions questions = likedQuestion.getQuestionsId();
//
//            // Assuming Questions has a reference to Options
//            Set<String> options = questions.getOptions();
//
//            // Assuming Questions has a reference to Tags
//            Set<String> tags = questions.getTags();
//
//            // Assuming QuestionsModel is a DTO class for Questions
//            questionsModel.setQuestion(questions.getQuestion());
//            questionsModel.setAge(questions.getAge());
////            questionsModel.setOptions(options);
//            questionsModel.setTags(tags);
//
//            questionsModels.add(questionsModel);
//        }
//
//        return questionsModels;
//    }


}
