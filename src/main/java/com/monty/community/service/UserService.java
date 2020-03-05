package com.monty.community.service;

import com.monty.community.mapper.UserMapper;
import com.monty.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void login(User user) {
        User user1=userMapper.findUserByAccountId(user.getAccountId());
        if (user1!=null){
            userMapper.update(user);
        }else {
            userMapper.insertUser(user);
        }
    }
}
