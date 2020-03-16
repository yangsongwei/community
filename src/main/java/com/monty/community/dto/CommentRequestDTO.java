package com.monty.community.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
