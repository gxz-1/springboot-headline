package com.gxz.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @TableName news_headline
 */

//news_ + headline
@Data
public class Headline implements Serializable {
    @TableId
    private Long hid;

    private String title;
    private String article;
    private Integer type;
    private Integer publisher;
    private Integer pageViews;
    private Date createTime;
    private Date updateTime;

    @Version
    @JsonIgnore//向前端返回json时不返回这个属性
    private Integer version;
    @JsonIgnore
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}