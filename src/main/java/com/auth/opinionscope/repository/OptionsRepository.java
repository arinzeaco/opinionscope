package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionsRepository extends JpaRepository<Options, Integer> {
//    Optional<Options> findById(long id);
   Options findByOptionsListId(long id);

}