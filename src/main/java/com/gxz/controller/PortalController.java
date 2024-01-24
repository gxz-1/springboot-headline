package com.gxz.controller;

import com.gxz.service.HeadlineService;
import com.gxz.service.PortalService;
import com.gxz.utils.Result;
import com.gxz.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("portal")
public class PortalController {
    @Autowired
    private PortalService portalService;

    @Autowired
    private HeadlineService headlineService;

    //首页获取所有类别
    @RequestMapping("findAllTypes")
    public Result findAllTypes(){
        Result result= portalService.findAllTypes();
        return result;
    }

    //分页查询
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result= headlineService.findNewsPage(portalVo);
        return result;
    }

    //根据新闻id查询新闻详情，并新增浏览量
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Long hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }



}
