package com.gxz.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class User implements Serializable {

    @TableId
    private Long uid;

    private String username;

    private String userPwd;

    private String nickName;

    @Version
    @JsonIgnore//向前端返回json时不返回这个属性
    private Integer version;
    @JsonIgnore
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}