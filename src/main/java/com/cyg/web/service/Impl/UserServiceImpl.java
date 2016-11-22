package com.cyg.web.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyg.web.dao.IUserMapper;
import com.cyg.web.entity.User;
import com.cyg.web.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserMapper iUserMapper;

    public User searchById(String id){
        return iUserMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insert(User user) {
        return iUserMapper.insert(user);
    }

    @Transactional
    public int delete(String id){
        return iUserMapper.deleteByPrimaryKey(id);
    }

}