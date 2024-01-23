package com.gxz.service;

import com.gxz.mapper.HeadlineMapper;
import com.gxz.utils.Result;
import com.gxz.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;

public interface HeadlineService {
    Result findNewsPage(PortalVo portalVo);
}
