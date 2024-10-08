package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagsRepository extends JpaRepository<Tags, Integer> {
    Tags getAllByTagId(long roleName);

}