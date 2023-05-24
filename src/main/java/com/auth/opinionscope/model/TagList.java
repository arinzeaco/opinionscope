package com.auth.opinionscope.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class TagList extends BaseEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long tagId;


    @ManyToMany(mappedBy = "tagList", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,
            targetEntity = Questions.class)
    private Set<Questions> questions = new HashSet<>();



}