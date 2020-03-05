package com.monty.community.controller;

import com.monty.community.mapper.QuestionMapper;
import com.monty.community.mapper.UserMapper;
import com.monty.community.model.Question;
import com.monty.community.model.User;
import com.monty.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id
            ,Model model){
        Question question=questionMapper.FindQuestionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("desciption",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id",defaultValue = "0") Integer id,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        User user=(User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }


        Question question=new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCommentCount(0);
        question.setLikeCount(0);
        question.setViewCount(0);
        questionService.createOrUpdateQuestion(id,question);
        return "redirect:/";
    }
}
