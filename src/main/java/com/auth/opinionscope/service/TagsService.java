package com.auth.opinionscope.service;

import com.auth.opinionscope.model.TagList;
import com.auth.opinionscope.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;



    public List<TagList> getTags() {

        List<TagList> savedUser = tagsRepository.findAll();
        return savedUser;
    }

    public TagList getTagListById() {
        int intValue = 10;
        long longValue1 = intValue; //
        TagList savedUser = tagsRepository.getAllByTagId(longValue1);
        return savedUser;
    }

    public boolean saveTags(TagList tagList) {
        boolean isSaved = false;

        TagList savedQuestion = tagsRepository.save(tagList);
        if (null != savedQuestion && savedQuestion.getTagId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }



}
