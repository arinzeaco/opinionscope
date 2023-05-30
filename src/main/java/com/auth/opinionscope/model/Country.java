package com.auth.opinionscope.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long countryId;

    @ManyToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST,
            targetEntity = Questions.class)
    private Set<Questions> country = new HashSet<>();

    private String country_name;

}
