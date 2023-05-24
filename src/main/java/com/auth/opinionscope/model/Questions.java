package com.auth.opinionscope.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Questions extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long questionId;

    @Size(min=120, message="Question must be at least 120 characters long")
    private String question;

    @Column(nullable = false)
    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "question_tag",
            joinColumns = {
                    @JoinColumn(name = "question_id", referencedColumnName = "questionId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "tagId")})
    private Set<TagList> tagList = new HashSet<>();
//
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "question_country",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "questionId"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "countryId"))
    private Set<Country> country = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "Options_list_id", referencedColumnName = "OptionsListId")
    private OptionsList options;
}
