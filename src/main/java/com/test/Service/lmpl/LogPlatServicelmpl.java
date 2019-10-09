package com.test.Service.lmpl;

import com.test.Dao.LogPlatDao;
import com.test.Entity.Platform;
import com.test.Service.LogPlatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogPlatServicelmpl implements LogPlatService {
    @Resource
    LogPlatDao logPlatDao;

    //新增平台
    public String platform_add(Platform platform) {
        //第一步，在platform_data表中插入数据
        int ret = logPlatDao.insertPlatform(platform.getPname(), platform.getPcode(),
                platform.getPurl(), platform.getPlog());
        if(ret <= 0)
            return "add_faile";

        //第二步，新建一张表，表名: platform+pid
        Integer Pid = logPlatDao.getLatestPid();
        System.out.println("表名：platform"+Pid);

        String tableName ="platform"+Pid;
        ret = logPlatDao.createTab(tableName);
        if(ret != 0)
            return "create_fail";

        //第三步,在新建表中插入根节点
        ret = logPlatDao.addTreeNode(tableName, 1, platform.getPname(),null,0,null,null);
        if(ret == 1) {
            return "success";
        }
        return "insert_fail";
    }

    //删除平台
    public String platform_delete(Integer pid) {
        //第一步，删除平台（platform_data表的一条记录）
        int ret = logPlatDao.deletePlatform(pid);
        if(ret < 0)
            return "fail";

        //第二步，删除平台的那张表
        ret = logPlatDao.deleteTab("platform" + pid.toString());
        if(ret < 0)
            return "fail";

        //第三步，删除permission表里的相关数据
        ret = logPlatDao.deletePermByPid(pid);
        System.out.println(ret);
        if(ret >= 0)
            return "success";
        return "fail";
    }

    //修改平台信息
    public String platform_change(Platform platform) {
        //如果修改平台名，需要修改平台表里对应的根结点名字
        Platform oldPlat = logPlatDao.findByPid(platform.getPid());
        if(!((platform.getPname()).equals(oldPlat.getPname()))){
            String tableName = "platform" + platform.getPid();
            logPlatDao.updateNodeName(tableName, platform.getPname(), 1);
        }

        //修改平台信息
        int ret = logPlatDao.updatePlatform(platform.getPid(), platform.getPname(),
                platform.getPcode(), platform.getPurl(), platform.getPlog());
        if(ret == 1) {
            return "success";
        }
        return "fail";
    }

    //查找平台信息
    public Platform platform_find(Integer pid) {
        return logPlatDao.findByPid(pid);
    }

    //查看所有平台信息
    public List<Platform> platform_findAll() {
        return logPlatDao.findAll();
    }
}
