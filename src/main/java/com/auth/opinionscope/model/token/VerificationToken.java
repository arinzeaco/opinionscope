//package com.auth.opinionscope.model.token;
//
//import com.auth.opinionscope.model.User;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.UUID;
//import java.util.Calendar;
//
//
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class VerificationToken {
//
//    @Id
//    @GeneratedValue
//    public Integer id;
//
//    private String Token;
//
//    private Date expiryDate;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    public User user;
//}