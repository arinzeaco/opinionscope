package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Options;
import com.auth.opinionscope.repository.OptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Service
public class OptionsService {

    @Autowired
    private OptionsRepository optionsRepository;



    public List<Options> getOptions() {

        List<Options> savedOptions = optionsRepository.findAll();
        return savedOptions;
    }
//
//    public Optional<OptionsList> getOptionsListById(long id) {
//
//        Optional<OptionsList> savedOptions = optionsListRepository.findById(id);
//        return savedOptions;
//    }
//    public OptionsList getOptionsListById() {
//        int intValue = 10;
//        long longValue1 = intValue; //
//        OptionsList savedUser = optionsListRepository.getAllByOptionsId(longValue1);
//        return savedUser;
//    }
//
    public boolean saveOptions(Options options) {
        boolean isSaved = false;

        Options savedOptions = optionsRepository.save(options);
        if (savedOptions.getOptionsListId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }



}
