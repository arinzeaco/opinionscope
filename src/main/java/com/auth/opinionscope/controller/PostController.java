package com.auth.opinionscope.controller;

import com.auth.opinionscope.dataModel.PostDto;
import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.repository.QuestionRepository;
import com.auth.opinionscope.repository.TagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/questions")
@RestController
public class PostController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagsRepository tagRepository;

    @PostMapping(value = "/add_question")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDTO) {
        Questions questions = new Questions();
        questions.setQuestion(postDTO.getTitle());
        questions.setAge(4);

//        Set<Tags> tags = new HashSet<>();
//        for (String tagContent : postDTO.getTagContents()) {
//            Tags tag = new Tags();
//            tag.setTagName(tagContent);
//            tags.add(tag); // Save the tag to generate its ID
//
//        }
//
//        questions.setTags(tags);
        questionRepository.save(questions);

        return ResponseEntity.ok("Post created successfully.");

    }
}