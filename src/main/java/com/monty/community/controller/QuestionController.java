package com.monty.community.controller;

import com.monty.community.dto.QuestionDTO;
import com.monty.community.mapper.QuestionMapper;
import com.monty.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(
            @PathVariable(name = "id") Integer questionId,
            Model model
    ){
        QuestionDTO questionDTO=questionService.FindQuestionById(questionId);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
