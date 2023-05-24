package com.auth.opinionscope.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Data
@Entity
public class OptionsList {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long OptionsListId;

    @OneToMany(mappedBy = "options", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,targetEntity = Questions.class)
    private Set<Questions> persons;

}