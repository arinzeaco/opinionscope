package com.auth.opinionscope.service;

import com.auth.opinionscope.model.VoteCount;
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



    public long getVoteById(long id) {

        long savedVote = voteRepository.countByOptionsListId(id);

        return savedVote;
    }
    public boolean getUserIdAndOptionsListId(long userId, long OptionsListId) {
//        boolean isVoted = voteRepository.findFirstByUserId(userId);
        Optional<VoteCount> optional = voteRepository.findByUserIdAndOptionsListId(userId,OptionsListId);

//        Optional< VoteCount > optional = voteRepository.findById(id2);
//
//        if (optional.isPresent()) {
//            System.out.println(optional.get());
//        } else {
//            System.out.printf("No employee found with id %d%n", id2);
//        }
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

        VoteCount saveVote= voteRepository.save(voteCount);
        if (null != saveVote && saveVote.getId() > 0)
        {
            isSaved = true;
        }

        return isSaved;
    }



}
