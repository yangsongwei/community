package com.monty.community.controller;

import com.monty.community.dto.PaginationDTO;
import com.monty.community.mapper.UserMapper;
import com.monty.community.model.User;
import com.monty.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(
            @PathVariable(name = "action") String action,
            Model model,
            HttpServletRequest httpServletRequest,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "3") Integer size
    ){
        User user=(User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return "/";
        }
        PaginationDTO paginationDTO =questionService.listQuestion(user.getId(),page,size);
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            model.addAttribute("pagination",paginationDTO);
        }else if ("replies".equals(action)){
            //replies层的内容
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";
    }
}
