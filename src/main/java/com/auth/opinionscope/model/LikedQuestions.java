package com.auth.opinionscope.model;

import com.auth.opinionscope.model.auth.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "liked_questions")
public class LikedQuestions extends BaseEntity {



    @Id
    @JsonIgnore
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "like_question_id")
    private Long likeQuestionId;

    @ManyToOne
    @JoinColumn(name = "questions_id")
    private Questions LikeQuestions;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserData UserDataLiked;
}