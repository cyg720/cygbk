package com.cyg.web.service;

import com.cyg.web.entity.User;

public interface  IUserService {
	User searchById(String id);

    int insert(User user);

    int delete(String id);
}
