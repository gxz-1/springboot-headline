package com.gxz.service;

import com.gxz.pojo.User;
import com.gxz.utils.Result;

public interface UserService {
    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
