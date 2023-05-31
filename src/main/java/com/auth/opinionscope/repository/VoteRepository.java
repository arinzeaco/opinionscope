package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Tags;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.model.VoteCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface VoteRepository extends JpaRepository<VoteCount, Integer> {

    long countByOptionsListId(long id);

    Optional<VoteCount> findByUserIdAndOptionsListId(long userId,long option);



}