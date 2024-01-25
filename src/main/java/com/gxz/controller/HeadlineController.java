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

    //添加新的新闻
    @PostMapping("publish")
    public Result publish(@RequestHeader String token, @RequestBody Headline headline){
        Result result = headlineService.publish(token,headline);
        return result;
    }

    //修改新闻
    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateHeadline(headline);
        return result;
    }
    //修改新闻后向前端回显
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return result;
    }

    //删除新闻
    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){
        Result result = headlineService.removeByHid(hid);
        return result;
    }
}
