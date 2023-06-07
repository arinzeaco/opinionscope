package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.auth.Country;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepository extends JpaRepository<Country, Integer> {

}