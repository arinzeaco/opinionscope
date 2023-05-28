package com.auth.opinionscope.service;

import com.auth.opinionscope.model.OptionsList;
import com.auth.opinionscope.model.TagList;
import com.auth.opinionscope.repository.OptionsListRepository;
import com.auth.opinionscope.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Service
public class OptionsListService {

    @Autowired
    private OptionsListRepository optionsListRepository;


//
//    public List<OptionsList> getOptions() {
//
//        List<OptionsList> savedOptions = optionsListRepository.findAll();
//        return savedOptions;
//    }
//
////    public Optional<OptionsList> getOptionsListById(long id) {
////
////        Optional<OptionsList> savedOptions = optionsListRepository.findById(id);
////        return savedOptions;
////    }
////    public OptionsList getOptionsListById() {
////        int intValue = 10;
////        long longValue1 = intValue; //
////        OptionsList savedUser = optionsListRepository.getAllByOptionsId(longValue1);
////        return savedUser;
////    }
//
//    public boolean saveOptions(OptionsList optionsList) {
//        boolean isSaved = false;
//
//        OptionsList savedOptions = optionsListRepository.save(optionsList);
//        if (null != savedOptions && savedOptions.getOptionsListId() > 0)
//        {
//            isSaved = true;
//        }
//        return isSaved;
//    }



}
