package com.test.Service.lmpl;

import com.test.Dao.PlatformDao;
import com.test.Dao.TreeDao;
import com.test.Entity.Platform;
import com.test.Service.PlatformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlatformServicelmpl implements PlatformService {
    @Resource
    TreeDao treeDao;
    @Resource
    PlatformDao platformDao;

    //新增平台
    public String platform_add(Platform platform) {
        //第一步，在platform_data表中插入数据
        int ret = platformDao.insertPlatform(platform.getPname(), platform.getPcode(),
                platform.getPurl(), platform.getPlog());
        if(ret < 0)
            return "add_faile";

        Integer Pid = platformDao.getLastPid();
        //第二步，新建一张表，表名为platform+pid
        System.out.println("表名：platform"+Pid);
        String table ="platform"+Pid;
        ret = platformDao.createTab(table);
        if(ret != 0)
            return "create_fail";

        //第三步,在新建表中插入根节点
        ret = treeDao.addTreeNode(table,platform.getPname(),null,0,null,null);
        if(ret == 1){
            return "success";
        }
        return "insert_fail";
    }

    //删除平台，删除platform_data表的一条记录，删除平台的那张表
    public String platform_delete(Integer pid) {
        int ret = platformDao.deletePlatform(pid);
        if(ret < 0)
            return "fail";
        ret = platformDao.deleteTab("platform" + pid.toString());
        if(ret == 0)
            return "success";
        return "fail";
    }

    //修改平台信息
    public String platform_change(Platform platform) {
        Platform p = new Platform();
        p = platformDao.findByPid(platform.getPid());
        if(!( ( platform.getPname() ).equals( p.getPname() ) ) ){ //判断 是否 更改平台名属性
            treeDao.changeTreeNodeCname("platform"+platform.getPid(),platform.getPname(),1);  //修改该平台对应的根结点名字
        }

        int ret = platformDao.updatePlatform(platform.getPid(), platform.getPname(),        //修改平台信息
                platform.getPcode(), platform.getPurl(), platform.getPlog());

        if(ret == 1)
            return "success";
        return "fail";
    }

    //查找平台信息
    public Platform platform_find(Integer pid) {
        return platformDao.findByPid(pid);
    }

    //查看所有平台信息
    public List<Platform> platform_findAll() {
        return platformDao.findAll();
    }
}
