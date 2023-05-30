package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Options;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionsListRepository extends JpaRepository<Options, Integer> {
//    Optional<OptionsList> findById(long id);
//   OptionsList getAllByOptionsId(long id);

}