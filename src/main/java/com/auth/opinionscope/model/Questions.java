package com.auth.opinionscope.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;


//@Builder

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="questions")
public class Questions extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "question_id")
    private long questionId;

    @Size(max=120, message="Question must be at least 120 characters long")
    private String question;

    @Min(value = 0, message = "Age must be a positive value")
    private int age;


    @Min(value = 0)
    private int likeCount;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "question_tag",
//            joinColumns = { @JoinColumn(name = "question_id", referencedColumnName = "questionId")},
//            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "tagId")})
//    private Set<String> tags = new HashSet<>();

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "question_tag",
//            joinColumns = { @JoinColumn(name = "question_id", referencedColumnName = "questionId")},
//            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "tagId")})
    private Set<String> tags = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "question_country",
//            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "questionId"),
//            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "countryId"))
    private Set<String> country = new HashSet<>();

//    @OneToMany( fetch = FetchType.EAGER,
//            cascade = CascadeType.PERSIST,targetEntity = Options.class)
    private Set<String> options = new HashSet<>();

    private Set<Long> optionsIDs = new HashSet<>();

    private Set<String> mustHaveTag = new HashSet<>();





//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "Options_list_id", referencedColumnName = "OptionsListId")
//    private OptionsList options;


}
