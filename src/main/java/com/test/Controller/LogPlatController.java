package com.test.Controller;

import com.test.Entity.Platform;
import com.test.Service.LogPlatService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/platform")
public class LogPlatController {

    @Resource
    LogPlatService logPlatService;

    //新增平台
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String platform_add(String pname, String pcode, String purl, String plog) {
        Platform platform = new Platform(0, pname, pcode, purl, plog);
        System.out.println("新增平台: " + platform);
        return logPlatService.platform_add(platform);
    }

    //删除平台
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String platform_delete(Integer pid) {
        System.out.println("删除平台: " + pid);
        return logPlatService.platform_delete(pid);
    }

    //修改平台信息
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String platform_change(Integer pid, String pname, String pcode, String purl, String plog) {
        Platform platform = new Platform(pid, pname, pcode, purl, plog);
        System.out.println("修改平台: " + platform);
        return logPlatService.platform_change(platform);
    }

    //查找平台信息
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Platform platform_find(Integer pid) {
        System.out.println("查找平台信息: " + pid);
        return logPlatService.platform_find(pid);
    }

    //查看所有平台信息
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public List<Platform> platform_findAll() {
        System.out.println("查看所有平台信息!!!");
        return logPlatService.platform_findAll();
    }

}
