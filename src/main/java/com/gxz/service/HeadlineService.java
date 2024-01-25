package com.gxz.service;

import com.gxz.pojo.Headline;
import com.gxz.utils.Result;
import com.gxz.vo.PortalVo;

public interface HeadlineService {
    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Long hid);

    Result publish(String token, Headline headline);

    Result findHeadlineByHid(Integer hid);

    Result updateHeadline(Headline headline);

    Result removeByHid(Integer hid);
}
