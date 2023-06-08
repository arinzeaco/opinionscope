package com.auth.opinionscope.service;

import com.auth.opinionscope.dataModel.request.ForgotPasswordModel;
import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.model.VoteCount;
import com.auth.opinionscope.model.auth.User;
import com.auth.opinionscope.repository.OptionsRepository;
import com.auth.opinionscope.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private OptionsRepository optionsRepository;




    public long getVoteById(long id) {

        long savedVote = voteRepository.countByOptionsId(id);

        return savedVote;
    }

    public boolean getUserIdAndQuestionIdAndOptionsId(Long userId, Long question, Long OptionsListId ) {
        Optional<VoteCount> optional = voteRepository.findByUserIdAndQuestionIdAndOptionsId(userId,question, OptionsListId);


        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.printf("No employee found with id %d%n", userId);
        }
        return optional.isPresent();
    }
//    public List<VoteCount> getVotes(long userId) {
//
//        List<VoteCount> voteCount=voteRepository.findFirstById();
//
//        return voteCount;
//    }


    public boolean saveVote(VoteCount voteCount) {
        boolean isSaved = false;
        Optional<VoteCount> findByUserIdAndOptionsListId = voteRepository.findByUserIdAndQuestionIdAndOptionsId
                (voteCount.getUserId(), voteCount.getQuestionId(),voteCount.getOptionsId());

        if(findByUserIdAndOptionsListId.isPresent()){
            findByUserIdAndOptionsListId.get().
                    setOptionsId(voteCount.getOptionsId());
            VoteCount saveVote = voteRepository.
                    save(findByUserIdAndOptionsListId.get());
            return saveVote.getUserId() != null;
        } else {
            Options findByOptionsListId = optionsRepository.findByOptionsId(voteCount.getOptionsId());
            findByOptionsListId.setOptions_count(findByOptionsListId.getOptions_count()+1);
            optionsRepository.save(findByOptionsListId);
            VoteCount saveVote = voteRepository.save(voteCount);
//            optionsRepository.
            if (saveVote.getId() > 0) {
                isSaved = true;
            }
        }
        return isSaved;
    }
}
