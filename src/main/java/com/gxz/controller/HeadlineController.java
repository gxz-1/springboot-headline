package com.gxz.controller;

import com.gxz.pojo.Headline;
import com.gxz.service.HeadlineService;
import com.gxz.utils.Result;
import com.gxz.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("headline")
public class HeadlineController {
    @Autowired
    private HeadlineService headlineService;

    @PostMapping("publish")
    public Result publish(@RequestHeader String token, @RequestBody Headline headline){
        Result result = headlineService.publish(token,headline);
        return result;
    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return result;
    }
}
