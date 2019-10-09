package com.test.Controller;

import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import com.test.Entity.Resdata;
import com.test.Service.SysFunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/tree")
public class SysFunController {

    @Autowired
    protected SysFunService sysFunService;

    /**
     * 获取  某个 平台的全部节点
     */
    @RequestMapping(value = "/getTree",method = RequestMethod.POST)
    public Resdata getTree(@RequestParam String pid){
        System.out.println("获取整棵树结构");
        return sysFunService.getTree(pid);
    }

    /**
     * 获取所有平台的根节点
     */
    @RequestMapping(value = "/getAllPid",method = RequestMethod.POST)
    public List<Resdata> getAllPid(){
        System.out.println("获取所有平台的根节点");
        return  sysFunService.getAllPid();
    }

    /**
     * 增加 某平台的 某节点
     * 备注：不需要上传节点的id字段（自增字段）
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addTreeNode(@RequestParam String pid,@RequestParam String cname,@RequestParam String ename,
                              @RequestParam Integer ppid,@RequestParam String notes, @RequestParam String tag){
        System.out.println("增加平台："+pid+"的节点："+cname);
        PlatformTree platformTree = new PlatformTree(cname, ename, ppid, notes, tag);
        return  sysFunService.addTreeNode(pid, platformTree);
    }

    /**
     *修改 某平台的 某节点
     */
    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public String changeTreeNode(@RequestParam String pid,@RequestParam String id,@RequestParam String cname,
                                 @RequestParam String ename, @RequestParam String ppid,@RequestParam String notes,
                                 @RequestParam String tag){
        System.out.println("修改平台："+pid+"的节点:"+id+"的信息");
        PlatformTree platformTree = new PlatformTree(Integer.parseInt(id),cname,ename,Integer.parseInt(ppid),notes,tag);
        return  sysFunService.changeTreeNode(pid,platformTree);
    }

    /**
     *删除 某平台的 某节点（包括它的子节点）
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delTreeNode(@RequestParam String pid,@RequestParam String id){
        System.out.println("删除平台："+pid+"的节点（"+id+"）和它的子节点");
        return  sysFunService.deleteNode(pid,id);
    }

    /**
     *查找某平台 某节点的具体信息
     */
    @RequestMapping(value = "/findTreeNode",method = RequestMethod.POST)
    public Resdata findTreeNodeById(@RequestParam String pid,@RequestParam String id){
        System.out.println("查找平台："+pid+"节点id为："+id+"的节点信息");
        return sysFunService.findTreeNodeById(pid,id);
    }


    /**
     * 某用户 获取 某平台 权限内(Authorized) 的节点
     */
    @RequestMapping(value = "/getTreeAuthorized",method = RequestMethod.POST)
    public Resdata getTreeOrdinal(@RequestParam String uid,@RequestParam String pid){
        System.out.println("用户:"+uid+"获取 权限内 的 <树结构>");
        return sysFunService.getTreeOrdinal(Integer.parseInt(uid),Integer.parseInt(pid));
    }

    /**
     * 获取 某用户 权限内(Authorized) 的平台
     */
    @RequestMapping(value = "/getPlatAuthorized",method = RequestMethod.POST)
    public List<Platform> getPlatAuthorized(@RequestParam Integer uid){
        System.out.println("用户："+uid+"获取 权限内 的 <平台信息>");
        return sysFunService.getPlatAuthorized(uid);
    }

    //排序
    @RequestMapping(value = "/sortTree", method = RequestMethod.POST)
    public String sortTree(@RequestBody HashMap hashMap) {
        System.out.println(hashMap);
        return sysFunService.sortTree(hashMap);
    }

}
