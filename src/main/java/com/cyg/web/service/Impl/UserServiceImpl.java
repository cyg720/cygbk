package com.cyg.web.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyg.web.dao.UserMapper;
import com.cyg.web.entity.User;
import com.cyg.web.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public User searchById(String id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Transactional
    public int delete(String id){
        return userMapper.deleteByPrimaryKey(id);
    }

}