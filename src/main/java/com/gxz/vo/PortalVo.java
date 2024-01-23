package com.gxz.vo;

import lombok.Data;

//自定义的类，用于接收前端的json
@Data
public class PortalVo {
    private String keyWords;//标题关键字
    private Integer type=0;//新闻类型,0表示所有
    private Integer pageNum=1;//页码数
    private Integer pageSize=10;//页大小
}
