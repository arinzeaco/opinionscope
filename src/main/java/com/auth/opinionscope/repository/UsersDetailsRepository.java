package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.auth.UsersDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDetailsRepository extends JpaRepository<UsersDetails, Long> {

}