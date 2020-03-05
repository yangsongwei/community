package com.monty.community.controller;

import com.monty.community.dto.PaginationDTO;
import com.monty.community.dto.QuestionDTO;
import com.monty.community.mapper.UserMapper;
import com.monty.community.model.User;
import com.monty.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HelloController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "2") Integer size){
        PaginationDTO paginationDTO =questionService.listQuestion(page,size);
        model.addAttribute("pagination",paginationDTO);
        return "index";
    }
}
