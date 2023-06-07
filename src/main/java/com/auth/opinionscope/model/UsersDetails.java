package com.auth.opinionscope.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class UsersDetails extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long UsersDetailsId;


//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")


    private Set<String> answeredQuests = new HashSet<>();
}