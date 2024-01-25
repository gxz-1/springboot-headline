package com.gxz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxz.mapper.HeadlineMapper;
import com.gxz.pojo.Headline;
import com.gxz.service.HeadlineService;
import com.gxz.utils.JwtHelper;
import com.gxz.utils.Result;
import com.gxz.vo.HeadlineVo;
import com.gxz.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class HeadlineServiceImpl implements HeadlineService {
    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

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

    @Override
    public Result showHeadlineDetail(Long hid) {
        //1.自定义多表查询(顺便查询乐观锁的version，便于更新浏览量)
        Map m = headlineMapper.queryDetailMap(hid);
        //2.修改浏览量+1
        Headline headline=new Headline();
        headline.setHid(hid);
        headline.setVersion((Integer) m.get("version"));//乐观锁，检查版本号
        headline.setPageViews((Integer) m.get("pageViews")+1);
        headlineMapper.updateById(headline);//headline为null的字段不修改
        //3.数据封装并返回
        m.remove("version");
        Map data=new HashMap();
        data.put("headline",m);
        return Result.ok(data);
    }

    @Override
    public Result publish(String token, Headline headline) {
        //1.根据token查询用户信息
        Long userId = jwtHelper.getUserId(token);
        //2.构造插入数据
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        //3.插入数据
        headlineMapper.insert(headline);
        return Result.ok(null);
    }


    @Override
    public Result updateHeadline(Headline headline) {
        //1.查询最新的版本号version
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);//保证乐观锁：执行update的时候检查传入version与实际version是否一致
        //2.更新UpdateTime字段
        headline.setUpdateTime(new Date());
        //3.执行update
        headlineMapper.updateById(headline);
        return Result.ok(null);
    }

    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        Map data=new HashMap();
        data.put("headline",headline);
        return Result.ok(data);
    }

    @Override
    public Result removeByHid(Integer hid) {
        int rows = headlineMapper.deleteById(hid);
        return Result.ok(null);
    }
}
