package com.monty.community.dto;

import com.monty.community.model.Question;
import com.monty.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Question question;
    private User user;

    public QuestionDTO(Question question, User user) {
        this.question=question;
        this.user=user;
    }
    public QuestionDTO(){

    }
}
