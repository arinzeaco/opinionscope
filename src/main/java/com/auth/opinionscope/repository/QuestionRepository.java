package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Questions, Integer> {

}