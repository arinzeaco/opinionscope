package com.auth.opinionscope.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table(name="tags")
public class Tags extends BaseEntity{


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long tagId;

    public String tagName;

//    @ManyToMany(mappedBy = "tagList", fetch = FetchType.LAZY,
//            targetEntity = Questions.class)
//    private Set<Questions> questions = new HashSet<>();


}