package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Country;
import com.auth.opinionscope.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepository extends JpaRepository<Country, Integer> {

}