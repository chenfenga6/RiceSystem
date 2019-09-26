package com.test.Controller;

import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import com.test.Entity.Resdata;
import com.test.Service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/tree")
public class TreeController {

    @Autowired
    protected TreeService treeService;
    /**
     * 获取  某个 平台的全部节点
     * @param pid  平台id
     * @return
     */
    @RequestMapping(value = "/getTree",method = RequestMethod.POST)
    public Resdata getTree(@RequestParam String pid){
        System.out.println("获取整棵树结构");
        return treeService.getTree(pid);
    }
    /**
     * 获取所有平台的根节点
     * @return
     */
    @RequestMapping(value = "/getAllPid",method = RequestMethod.POST)
    public List<Resdata> getAllPid(){
        System.out.println("获取所有平台的根节点");
        return  treeService.getAllPid();
    }

    /**
     * 增加 某平台的 某节点
     * 备注：不需要上传节点的id字段（自增字段）
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addTreeNode(@RequestParam String pid,@RequestParam String cname,@RequestParam String ename,
                              @RequestParam String ppid,@RequestParam String notes, @RequestParam String tag){
        System.out.println("增加平台："+pid+"的节点："+cname);
        PlatformTree platformTree = new PlatformTree(cname,ename,Integer.parseInt(ppid),notes,tag);
        return  treeService.addTreeNode(pid,platformTree);
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
        return  treeService.changeTreeNode(pid,platformTree);
    }

    /**
     *删除 某平台的 某节点（包括它的子节点）
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delTreeNode(@RequestParam String pid,@RequestParam String id){
        System.out.println("删除平台："+pid+"的节点（"+id+"）和它的子节点");
        return  treeService.delTreeNode(pid,id);
    }

    /**
     *查找某平台 某节点的具体信息
     */
    @RequestMapping(value = "/findTreeNode",method = RequestMethod.POST)
    public Resdata findTreeNodeById(@RequestParam String pid,@RequestParam String id){
        System.out.println("查找平台："+pid+"节点id为："+id+"的节点信息");
        return treeService.findTreeNodeById(pid,id);
    }

}
