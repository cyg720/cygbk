package com.zsx.web.service;

import com.zsx.web.entity.User;

public interface  UserService {
	User searchById(String id);

    int insert(User user);

    int delete(String id);
}
