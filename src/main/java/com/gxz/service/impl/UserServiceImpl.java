package com.gxz.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxz.mapper.UserMapper;
import com.gxz.pojo.User;
import com.gxz.service.UserService;
import com.gxz.utils.JwtHelper;
import com.gxz.utils.MD5Util;
import com.gxz.utils.Result;
import com.gxz.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        User RealUser = userMapper.selectOne(wrapper);
        if(RealUser==null){//查询不到账号
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //验证密码（前端传明文密码，数据库中密码经过MD5加密）
        String pwd=user.getUserPwd();
        if(!StringUtils.isEmpty(pwd) && MD5Util.encrypt(pwd).equals(RealUser.getUserPwd())){
            //密码正确:根据用户id生成token并返回
            String token = jwtHelper.createToken(RealUser.getUid());
            Map data=new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }
        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result checkLogin(String token) {
        if(jwtHelper.isExpiration(token)){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }

    @Override
    public Result getUserInfo(String token) {
        //1.校验token有效期
        boolean expiration = jwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        //2.根据token解析用户id
        Long userId = jwtHelper.getUserId(token);
        //3.根据用户id查询用户数据
        User user = userMapper.selectById(userId);//若报错需要在主键上添加@TableId
        user.setUserPwd("");//密码置空
        //4.返回用户数据
        Map data=new HashMap();
        data.put("loginUser",user);
        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        QueryWrapper wrapper= new QueryWrapper<User>();
        wrapper.eq("username",username);
        User user = userMapper.selectOne(wrapper);
        if(user==null){//账号名不存在
            return Result.ok(null);
        }
        //账号名已被使用
        return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }

    @Override
    public Result regist(User user) {
        //再次检查账号名是否存在
        QueryWrapper wrapper= new QueryWrapper<User>();
        wrapper.eq("username",user.getUsername());
        if(userMapper.selectOne(wrapper)!=null){//账号名已被使用
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        //添加用户
        String encodePwd=MD5Util.encrypt(user.getUserPwd());
        user.setUserPwd(encodePwd);
        userMapper.insert(user);
        return Result.ok(null);
    }




}
