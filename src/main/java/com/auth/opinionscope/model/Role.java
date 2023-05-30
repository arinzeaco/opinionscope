package com.auth.opinionscope.model;

import com.auth.opinionscope.config.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.auth.opinionscope.config.Permission.*;


@RequiredArgsConstructor
public enum Role {

    USERS,
    ADMIN,
    MANAGER



}