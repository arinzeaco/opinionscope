package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Tags;
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



    public List<Tags> getTags() {

        List<Tags> savedUser = tagsRepository.findAll();
        return savedUser;
    }



    public boolean saveTags(Tags tags) {
        boolean isSaved = false;

        Tags savedQuestion = tagsRepository.save(tags);
        if (null != savedQuestion && savedQuestion.getTagId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }



}
