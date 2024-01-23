package com.gxz.service.impl;

import com.gxz.mapper.TypeMapper;
import com.gxz.pojo.Type;
import com.gxz.service.PortalService;
import com.gxz.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PortalServiceImpl implements PortalService {

    @Autowired
    private  TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> list = typeMapper.selectList(null);
        return Result.ok(list);
    }
}
