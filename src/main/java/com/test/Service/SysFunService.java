package com.test.Service;

import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import com.test.Entity.Resdata;
import com.test.Entity.Votetree;

import java.util.HashMap;
import java.util.List;

public interface SysFunService {
    //获取某个平台的树结构
    Resdata getTree(String pid);

    //获取所有平台的根节点
    List<Resdata> getAllPid();

    //增加 某平台的 某节点
    String addTreeNode(String pid,PlatformTree platformTree);

    //修改 某平台的 某节点
    String changeTreeNode(String pid,PlatformTree platformTree);

    //通过id查找这个树节点 全部信息和父节点名称
    Resdata findTreeNodeById(String pid,String id);

    //删除 某平台的 某节点（包括它的子节点）
    String deleteNode(String pid, String nid);

    //排序(等待实现节点拖动的排序--暂时不用)
    String sortTree(HashMap hashMap);

    //排序（两个节点之间的交换）
    String treeNodeSort(int pid,int sourceId,int targetId);

    /***************************权限测试**********************/

    //某用户 获取权限内的 某平台 节点
    Resdata getTreeOrdinal(Integer uid,Integer pid);

    //获取 某用户 权限内(Authorized) 的平台
    List<Platform> getPlatAuthorized(Integer uid);

}
