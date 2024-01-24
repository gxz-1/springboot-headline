package com.gxz.controller;

import com.gxz.pojo.User;
import com.gxz.service.UserService;
import com.gxz.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin//允许前端跨域访问
public class UserController {
    @Autowired
    private UserService userService;

    //登录并返回token
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        return result;
    }

    //根据请求头中的token判断是否登录
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        Result result = userService.checkLogin(token);
        return result;
    }

    //根据请求头的token返回用户信息
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }

    //检查用户名是否被使用
    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }

    //用户注册
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }



}
