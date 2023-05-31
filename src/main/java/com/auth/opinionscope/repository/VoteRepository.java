package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Tags;
import com.auth.opinionscope.model.VoteCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface VoteRepository extends JpaRepository<VoteCount, Integer> {
    //    List<VoteCount> findByOptionsListId(long id);
//    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    long countByOptionsListId(long id);
//    long countByLastname(String lastname);


//    @Transactional
//    @Modifying
//    @Query("SELECT COUNT(*) FROM votes v WHERE v.options_list_id=:id")
//    int getVoteCountById(long id);
}