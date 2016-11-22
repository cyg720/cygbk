package com.cyg.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.cyg.web.entity.User;
import com.cyg.web.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 获取用户
     */
    @RequestMapping("/getUserList")
    public void getJson(HttpServletRequest request, HttpServletResponse response)
            throws IOException {        
        User user = iUserService.searchById("1");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(JSON.toJSONString(user));
        out.flush();
        out.close();
    }
}