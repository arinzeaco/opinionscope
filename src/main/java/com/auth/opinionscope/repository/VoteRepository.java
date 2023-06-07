package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.VoteCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VoteRepository extends JpaRepository<VoteCount, Integer> {

    long countByOptionsListId(long id);

    Optional<VoteCount> findByUserIdAndOptionsListId(long userId,long option);



}