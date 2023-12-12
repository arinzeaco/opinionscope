//package com.auth.opinionscope.model;
//
//import com.auth.opinionscope.model.auth.UserData;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.GenericGenerator;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="comments")
//public class Comments extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
//    @GenericGenerator(name = "native",strategy = "native")
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Column(name = "postId", nullable = false)
//    private Long postId;
//
//    @Size(max=1000)
//    private String comment;
//
//    @Size(max=100)
//    private String postType;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserData UserDataCreated;
//}
