package com.auth.opinionscope.service;

import com.auth.opinionscope.model.VoteCount;
import com.auth.opinionscope.repository.VoteRepository;
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
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;



    public long getVoteById(long id) {

        long savedVote = voteRepository.countByOptionsListId(id);

        return savedVote;
    }

    public List<VoteCount> getVotes() {

        List<VoteCount> voteCount=voteRepository.findAll();

        return voteCount;
    }



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
