package com.auth.opinionscope.service;

import com.auth.opinionscope.model.Country;
import com.auth.opinionscope.model.Tags;
import com.auth.opinionscope.repository.CountryRepository;
import com.auth.opinionscope.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;



    public List<Country> getCountry() {

        List<Country> saveCountry = countryRepository.findAll();
        return saveCountry;
    }



    public boolean saveCountry(Country country) {
        boolean isSaved = false;

        Country saveCountry = countryRepository.save(country);
        if (null != saveCountry && saveCountry.getCountryId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }



}
