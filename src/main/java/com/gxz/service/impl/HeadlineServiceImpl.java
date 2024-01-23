package com.gxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxz.mapper.HeadlineMapper;
import com.gxz.pojo.Headline;
import com.gxz.service.HeadlineService;
import com.gxz.utils.Result;
import com.gxz.vo.HeadlineVo;
import com.gxz.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HeadlineServiceImpl implements HeadlineService {
    @Autowired
    private HeadlineMapper headlineMapper;

    @Override
    public Result findNewsPage(PortalVo portalVo) {
        //1.生成查询条件
        QueryWrapper<Headline> wrapper=new QueryWrapper<>();
        //条件1：前端传入关键字时,根据title名进行关键字模糊查询
        String keyWords = portalVo.getKeyWords();
        wrapper.like(keyWords!=null && keyWords.length()>0,"title",keyWords);
        //条件2：限定新闻类型时，查询指定类型
        Integer type = portalVo.getType();
        wrapper.eq(type!=0,"type",type);
        //2.进行分页数据查询
        Page<Headline> page=new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectPage(page, wrapper);
        List<Headline> headlineList = page.getRecords();
        //3.进行数据处理
        List<HeadlineVo> headlineVoList=new ArrayList<>();
        for(Headline headline:headlineList){
            headlineVoList.add(new HeadlineVo(headline));
        }
        //4.数据封装并返回
        Map pageInfo=new HashMap();
        pageInfo.put("pageData",headlineVoList);//返回的数据
        pageInfo.put("pageNum",page.getCurrent());//当前页码
        pageInfo.put("pageSize",page.getSize());//页大小
        pageInfo.put("totalPage",page.getPages());//总页数
        pageInfo.put("totalSize",page.getTotal());//总条数
        Map data=new HashMap();
        data.put("pageInfo",pageInfo);
        return Result.ok(data);
    }
}
