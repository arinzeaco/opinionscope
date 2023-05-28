//package com.auth.opinionscope;
//
//import com.auth.opinionscope.model.Country;
//import com.auth.opinionscope.model.OptionsList;
//import com.auth.opinionscope.model.Questions;
//import com.auth.opinionscope.model.TagList;
//import com.auth.opinionscope.repository.OptionsListRepository;
//import com.auth.opinionscope.repository.QuestionsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final QuestionsRepository questionsRepository;
//    private final OptionsListRepository optionsListRepository;
//    private final TagListRepository tagListRepository;
//
//    @Autowired
//    public DataInitializer(QuestionsRepository questionsRepository, OptionsListRepository optionsListRepository, TagListRepository tagListRepository) {
//        this.questionsRepository = questionsRepository;
//        this.optionsListRepository = optionsListRepository;
//        this.tagListRepository = tagListRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        // Create and save OptionsList
//        OptionsList optionsList = new OptionsList();
//        optionsListRepository.save(optionsList);
//
//        // Create and save TagList
//        TagList tagList = new TagList();
//        tagListRepository.save(tagList);
//
//        // Create and save Questions
//        Questions question = new Questions();
//        question.setQuestion("Sample question");
//        question.setAge(20);
//        question.setOptions(optionsList);
//        question.getTagList().add(tagList);
//        question.getCountry().add(new Country(/* country data */));
////        questionsRepository.save(question);
//    }
//}