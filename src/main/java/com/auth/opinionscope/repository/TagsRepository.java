package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.TagList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TagsRepository extends JpaRepository<TagList, Integer> {
    TagList getAllByTagId(long roleName);

}