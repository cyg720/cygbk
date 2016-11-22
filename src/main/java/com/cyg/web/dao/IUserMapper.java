package com.cyg.web.dao;

import org.apache.ibatis.annotations.Param;

import com.cyg.web.entity.User;

public interface IUserMapper {

    int deleteByPrimaryKey(@Param(value="id")String id);

    int insert(User user);

    User selectByPrimaryKey(@Param(value="id")String id);
}