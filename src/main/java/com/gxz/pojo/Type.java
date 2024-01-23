package com.gxz.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @TableName news_type
 */
@Data
public class Type implements Serializable {

    @TableId
    private Long tid;

    private String tname;
    @Version
    @JsonIgnore//向前端返回json时不返回这个属性
    private Integer version;
    @JsonIgnore
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}