package com.test.Service;

import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import com.test.Entity.Resdata;

import java.util.List;

public interface TreeService {
    //获取某个平台的树结构
    Resdata getTree(String pid);
    //获取所有平台的根节点
    List<Resdata> getAllPid();
    //增加 某平台的 某节点
    String addTreeNode(String pid,PlatformTree platformTree);
    //修改 某平台的 某节点
    String changeTreeNode(String pid,PlatformTree platformTree);
    //删除 某平台的 某节点（包括它的子节点）
    String delTreeNode(String pid,String id);
    //通过id查找这个树节点 全部信息和父节点名称
    Resdata findTreeNodeById(String pid,String id);


}
