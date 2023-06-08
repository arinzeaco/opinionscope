package com.auth.opinionscope.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Options {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "options_id")
    private long optionsId;

    private String options_name;

    private int options_count;


//    @ManyToOne(fetch = FetchType.LAZY,
//            cascade = CascadeType.PERSIST,targetEntity = Questions.class)
//    private OptionsList optionList;

}