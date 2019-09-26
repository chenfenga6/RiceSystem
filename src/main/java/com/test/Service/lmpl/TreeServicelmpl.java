package com.test.Service.lmpl;

import com.test.Dao.PlatformDao;
import com.test.Dao.TreeDao;
import com.test.Entity.*;
import com.test.Service.TreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TreeServicelmpl implements TreeService {
    @Resource
    private PlatformDao platformDao;
    @Resource
    private TreeDao treeDao;


    @Override
    //获取某个平台的树结构
    public Resdata getTree(String pid) {
        Platform platform = new Platform();
        platform = platformDao.findByPid(Integer.parseInt(pid));
        if(platform == null){
            System.out.println("没有找到该平台！");
            return null;
        }
        String table = "platform"+platform.getPid();                //获取到相对应平台的数据库表名称
        PlatformTree tree = new PlatformTree();
        tree=treeDao.findById(table,1);                         //找到相对应平台的父亲节点

        List<PlatformTree> platformTreeList = new ArrayList<>();
        platformTreeList=getNextSubSet(table,tree);                 //查找所有的子节点

        Votetree result = new Votetree();
        result = treeToVote(tree);
        result.setChildren(getChildrenToVote(platformTreeList));    //将所有节点转换成VoteTree对象 格式

        List<Votetree> list = new ArrayList<>();
        list.add(result);

        return new Resdata(platform.getPid(),platform.getPname(),list);
    }

    @Override
    //获取所有平台的根节点
    public List<Resdata> getAllPid() {
        List<Resdata> resdata = new ArrayList<>();
        List<Platform> platformList = new ArrayList<>();
        platformList = treeDao.findAllPlatformPid();
        for (Platform p : platformList){
            Resdata data = new Resdata();
            data.setId(p.getPid());
            data.setName(p.getPname());
            resdata.add(data);
        }
        return resdata;
    }

    @Override
    //增加 某平台的 某节点
    public String addTreeNode(String pid,PlatformTree p) {
        if (p.getCname()== null){
            return "请输入正确的节点格式！";
        }
        String table = "platform"+pid;
        PlatformTree platformTree = new PlatformTree();
        platformTree = treeDao.findByCname(table,p.getCname());
        if(platformTree != null){
            return "该节点已存在！不可重名";
        }
         if(treeDao.addTreeNode(table,p.getCname(),p.getEname(),p.getPid(),p.getNotes(),p.getNotes()) == 1){
             return "success";
         }
        return "faile";
    }

    @Override
    //修改 某平台的 某节点
    public String changeTreeNode(String pid, PlatformTree p) {
        if (p.getId() == null){
            return "请输入要修改的节点id！";
        }
        String table = "platform"+pid;
        if(treeDao.changeTreeNode(table,p.getId(),p.getCname(),p.getEname(),p.getPid(),p.getNotes(),p.getTag()) == 1) {
            return "success";
        }
        return "faile";
    }

    @Override
    //删除 某平台的 某节点（包括它的子节点）
    public String delTreeNode(String pid, String id) {
        String table = "platform"+pid;
        PlatformTree platformTree = new PlatformTree();
        platformTree = treeDao.findById(table,Integer.parseInt(id));
        if(platformTree.getPid() == 0){
            return "该节点为根节点不可删除！";
        }
        List<PlatformTree> plist = new ArrayList<>();
        plist = getDeeptLevel(table,platformTree);
        for(PlatformTree pt : plist){
            treeDao.delTreeNode(table,pt.getId());
        }
         if(treeDao.delTreeNode(table,Integer.parseInt(id)) == 1){
             return "success";
         }
        return "faile";
    }

    @Override
    //通过id查找这个树节点 全部信息和父节点名称
    public Resdata findTreeNodeById(String pid,String id) {
        PlatformTree node = new PlatformTree();
        node = getTabAndNode(pid,id);
        List<PlatformTree> list = new ArrayList<>();
        list.add(node);
        Resdata res = new Resdata();
        res.setData(list);                      //把当前节点信息存入 data 中
        Integer father = node.getPid();
        if(father == 0){
            res.setName("无");
        }else {
            res.setName(treeDao.findById("platform"+pid,node.getPid()).getCname());
        }
        return res;
    }


    /**********************************方法************************************/
    //将 PlatformTree 对象List   转换成 Votetree 对象List
    public List<Votetree> getChildrenToVote(List<PlatformTree> platformTreeList){
        List<Votetree> votetreeList = new ArrayList<>();
        for(PlatformTree tr : platformTreeList ){
            Votetree votetree = new Votetree();
            votetree = treeToVote(tr);
            if(tr.getChildren() != null){
                votetree.setChildren(getChildrenToVote(tr.getChildren()));
            }
            votetreeList.add(votetree);
        }
        return votetreeList;
    }

    //将对象 PlatformTree 转换成 Votetree 对象
    public Votetree treeToVote(PlatformTree p){
        Votetree result = new Votetree();
        result.setId(p.getId());
        result.setTitle(p.getCname());
        result.setSpread(true);
        result.setChildren(p.getChildren());
        return result;
    }

    //遍历出整棵树
    public List<PlatformTree> getNextSubSet(String table,PlatformTree platformTree){
        List<PlatformTree> platformTreeList = new ArrayList<>();
        platformTreeList = treeDao.findByPid(table,platformTree.getId());

        for (PlatformTree ts : platformTreeList){
            List<PlatformTree> list = getDeeptLevel(table,ts);
            if(list.size() > 0){
                for(int i = 0 ; i < list.size();i++){
                    list.get(i).setChildren(getNextSubSet(table,list.get(i)));
                }
            }
            ts.setChildren(list);
        }
        return platformTreeList;
    }

    // 查找 该节点 的所有孩子节点
    public List<PlatformTree> getDeeptLevel(String table,PlatformTree platformTree){
        List<PlatformTree> platformTreeList = new ArrayList<>();
        platformTreeList = treeDao.findByPid(table,platformTree.getId());
        if(platformTreeList.size() > 0){
            for(int i = 0 ; i < platformTreeList.size(); i ++){
                getDeeptLevel(table,platformTreeList.get(i));
            }
        }
        return platformTreeList;
    }

    //确定表名 + 按照id查找当前信息
    public PlatformTree getTabAndNode(String pid, String id){
        String table = "platform"+pid;
        PlatformTree p = new PlatformTree();
        p = treeDao.findById(table,Integer.parseInt(id));
        return p;
    }

    /**********************************权限管理方法************************************/
    //向Permission（角色权限表）增加角色权限





}
