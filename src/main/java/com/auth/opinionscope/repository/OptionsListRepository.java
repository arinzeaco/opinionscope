package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.OptionsList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionsListRepository extends JpaRepository<OptionsList, Integer> {
//    Optional<OptionsList> findById(long id);
//   OptionsList getAllByOptionsId(long id);

}