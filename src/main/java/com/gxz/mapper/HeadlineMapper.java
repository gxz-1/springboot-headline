package com.gxz.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxz.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxz.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface HeadlineMapper extends BaseMapper<Headline> {

    Map queryDetailMap(Long hid);
}




