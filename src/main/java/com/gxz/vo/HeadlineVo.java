package com.gxz.vo;

import com.gxz.pojo.Headline;
import lombok.Data;

import java.util.Date;
import java.util.concurrent.TimeUnit;

//返回给前端的新闻查询数据
@Data
public class HeadlineVo {
    private Long hid;
    private String title;
    private Long type;
    private Integer pageViews;
    private Long pastHours;
    private Long publisher;

    public HeadlineVo(Headline headline){
        this.hid=headline.getHid();
        this.title=headline.getTitle();
        this.type=headline.getType();
        this.pageViews=headline.getPageViews();
        Long duration=new Date().getTime() - headline.getCreateTime().getTime();
        this.pastHours = TimeUnit.HOURS.convert(duration, TimeUnit.MILLISECONDS);
        this.publisher=headline.getPublisher();
    }
}
