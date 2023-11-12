package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.LikedQuestions;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.model.auth.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface LikedQuestionsRepository extends JpaRepository<LikedQuestions, Long> {

//    @Query("SELECT lq FROM LikedQuestions lq WHERE lq.UserDataLiked.userId = :user_id")
//    List<LikedQuestions> findAllByUserLiked(Long userId);
//    List<Questions> findLikedQuestionsByUserId(Long userId);

    @Query("SELECT q FROM Questions q " +
            "JOIN LikedQuestions lq ON lq.LikeQuestions = q " +
            "JOIN lq.UserDataLiked udl WHERE udl.userId = :userId")
    List<Questions> findLikedQuestionsByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LikedQuestions l WHERE l.LikeQuestions.questionId = :questionId AND l.UserDataLiked.userId = :userId")
    void deleteByQuestionsAndUserDataLiked(
            @Param("questionId") Long questionId,
            @Param("userId") Long userId
    );

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LikedQuestions l WHERE l.LikeQuestions = :likeQuestions AND l.UserDataLiked = :userData")
    boolean existsByLikeQuestionsAndUserDataLiked(Questions likeQuestions, UserData userData);

}