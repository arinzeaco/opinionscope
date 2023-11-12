package com.auth.opinionscope.model.auth;


import com.auth.opinionscope.model.BaseEntity;
import com.auth.opinionscope.model.LikedQuestions;
import com.auth.opinionscope.model.Questions;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class UsersDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long UsersDetailsId;
    //    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Set<Questions> answeredQuests = new HashSet<>();
//
//    private Set<Questions> bookMarkedQuestions = new HashSet<>();
//
//    private Set<Questions> likedQuestions = new HashSet<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<LikedQuestions> likedQuestions = new HashSet<>();

    private String profileImageUrl;

    private Set<String> favoriteTag;

    private String sport;

    private String sportTeam;

    private String religion;

}