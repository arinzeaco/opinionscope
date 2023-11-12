package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.model.auth.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface QuestionRepository extends JpaRepository<Questions, Long> {

    Optional<Questions> findByQuestionId(Long questionId);


}