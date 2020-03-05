package com.monty.community.mapper;

import com.monty.community.dto.QuestionDTO;
import com.monty.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void createQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") int offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Long userId, @Param(value = "offset") int offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question where creator=#{userId}")
    int countByUserId(@Param(value = "userId") Long userId);

    @Select("select * from question where id=#{questionId}")
    Question FindQuestionById(@Param(value = "questionId") Integer questionId);

    @Update("update question set title='${title}' ,description='${description}' ,gmt_modified=${gmtModified} ,tag='${tag}' where id=${id}")
    void updateQuestion(Question question);
}
