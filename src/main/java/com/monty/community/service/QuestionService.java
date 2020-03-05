package com.monty.community.service;

import com.monty.community.dto.PaginationDTO;
import com.monty.community.dto.QuestionDTO;
import com.monty.community.mapper.QuestionMapper;
import com.monty.community.mapper.UserMapper;
import com.monty.community.model.Question;
import com.monty.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO listQuestion(Integer page, Integer size){
        if (page<1){
            page=1;
        }
        int offset=(page-1)*size;
        PaginationDTO paginationDTO=new PaginationDTO();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        List<Question> questionList=questionMapper.list(offset,size);

        for(Question question : questionList){
            User user=userMapper.findUserById(question.getCreator());
            questionDTOS.add(new QuestionDTO(question,user));
        }

        paginationDTO.setQuestionDTOList(questionDTOS);
        paginationDTO.setCurrentPage(page);
        int totalPageCount=questionMapper.count();
        paginationDTO.setPagination(totalPageCount,page,size);
     return paginationDTO;
    }

    public PaginationDTO listQuestion(Long userId, Integer page, Integer size) {
        if (page<1){
            page=1;
        }
        int offset=(page-1)*size;
        PaginationDTO paginationDTO=new PaginationDTO();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        List<Question> questionList=questionMapper.listByUserId(userId,offset,size);

        for(Question question : questionList){
            User user=userMapper.findUserById(question.getCreator());
            questionDTOS.add(new QuestionDTO(question,user));
        }

        paginationDTO.setQuestionDTOList(questionDTOS);
        paginationDTO.setCurrentPage(page);
        int totalPageCount=questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalPageCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO FindQuestionById(Integer questionId) {
        QuestionDTO questionDTO=new QuestionDTO();
        questionDTO.setQuestion(questionMapper.FindQuestionById(questionId));
        questionDTO.setUser(userMapper.findUserById(questionDTO.getQuestion().getCreator()));
        return questionDTO;
    }

    public void createOrUpdateQuestion(Integer id, Question question) {
        if(0==id){
            questionMapper.createQuestion(question);
        }else {
            question.setId(id);
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updateQuestion(question);
        }
    }
}
