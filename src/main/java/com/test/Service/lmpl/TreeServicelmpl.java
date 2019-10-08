package com.test.Service.lmpl;

import com.test.Dao.PermissionDao;
import com.test.Dao.PlatformDao;
import com.test.Dao.TreeDao;
import com.test.Dao.UserDao;
import com.test.Entity.*;
import com.test.Service.TreeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TreeServicelmpl implements TreeService {
    @Resource
    PlatformDao platformDao;
    @Resource
    TreeDao treeDao;
    @Resource
    UserDao userDao;
    @Resource
    PermissionDao permissionDao;

    @Override
    //获取某个平台的树结构(无权限)
    public Resdata getTree(String pid) {
        Platform platform = new Platform();
        platform = platformDao.findByPid(Integer.parseInt(pid));
        if(platform == null){
            System.out.println("没有找到该平台！");
            return null;
        }
        String table = "platform"+platform.getPid();                //获取到相对应平台的数据库表名称
        PlatformTree Rootree =treeDao.findById(table,1);        //找到相对应平台的父亲节点

        Votetree result = getWholeTree(table,Rootree);             //查找所有的子节点

        List<Votetree> list = new ArrayList<>();
        list.add(result);

        return new Resdata(platform.getPid(),platform.getPname(),list);
    }

    @Override
    //获取某个平台的树结构(有权限)
    public Resdata getTreeOrdinal(Integer uid,Integer pid){
        String table ="platform"+pid;

        User user = userDao.findById(uid);                                  //获取该 <用户信息>
        if(user.getRoleId() == null || user.getRoleId() == 0 ){
            System.out.println("该用户没有开辟权限");
            return null;
        }
        Integer Role_id = user.getRoleId();                                 //获取该用户的 <权限id>
        System.out.println("用户："+user.getUname()+"Role_id="+Role_id);
        List<Permission> permissionList = treeDao.findPermissionByRidAndPid(Role_id,pid);  //获取该用户 <所有权限节点信息>
        System.out.println("共有数据："+permissionList.size()+"条");
        if (permissionList.size() == 0) {
            System.out.println("该用户没有该表的权限!");
            return null;
        }
        List<Integer> arry = new ArrayList<>();
        for(Permission pn : permissionList){
            arry.add(pn.getnId());                                              //将<所有权限节点id>存入 List
        }
        PlatformTree tree = treeDao.findById(table,1);                  //查找table表的 根节点

        List<Votetree> list = new ArrayList<>();
        list.add(getWholeTree(table,tree,arry));                            //根据arry遍历出权限内的完整树
        return new Resdata(tree.getId(),tree.getCname(),list);
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
        if (p.getCname()== null) {
            return "请输入正确的节点格式！";
        }
        String table = "platform" + pid;
        PlatformTree platformTree = treeDao.findByCname(table,p.getCname());
        if(platformTree != null){
            return "该节点已存在！不可重名";
        }

        //得到目前该表最大的id值，加1为新增结点id
        Integer id = treeDao.getMaxId(table) + 1;
        System.out.println("maxId = " + id);

        int ret = treeDao.addTreeNode(table, id, p.getCname(),p.getEname(),p.getPid(),p.getNotes(), p.getNotes());
        if(ret == 1)
            return "success";
        return "fail";
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

    //删除 某平台的 某节点（包括它的子节点）
    @Override
    public String deleteNode(String pid, String nid) {
        String platname = "platform" + pid;
        PlatformTree platformTree = treeDao.findById(platname,Integer.parseInt(nid));
        if(platformTree.getPid() == 0){
            return "该节点为根节点不可删除！";
        }
        List<PlatformTree> platforms = treeDao.findByPid(platname,platformTree.getId());

        deletetreenode(platname,platforms,pid);
        List<Permission> permissions = permissionDao.findByPidAndNid(Integer.parseInt(pid),platformTree.getId());
        if (permissions.size() != 0){
            for(Permission p : permissions){
                permissionDao.deletePermByAll(p.getrId(),p.getnId(),p.getpId());
            }
        }
        treeDao.delTreeNode(platname,Integer.parseInt(nid));
        return "success";
    }

    //节点排序
    @Override
    public String sortTree(HashMap hashMap) {
        String tableName = "platform" + hashMap.get("id");
        List<HashMap> list = (List<HashMap>)hashMap.get("data");

        //第一步，将数据取出来
        PlatformTree tree = treeDao.findById(tableName, 1);

        tree = hashToTree(tableName, tree, list.get(0));
        System.out.println("排序后的tree： " + tree);

        //第二步，删掉数据库这张表的所有记录
        int ret = treeDao.deleteTable(tableName);
        if(ret < 0) {
            return "fail";
        }

        //第三步，将数据写进数据库
        writeToMyql(tableName, tree);

        return "success";
    }

    /**********************************方法************************************/
    //递归查找paltforms下所有子节点并删除
    public void deletetreenode(String platname,List<PlatformTree> platforms,String pid){
        if(platforms.size() != 0)
        {
            for(PlatformTree p:platforms){
                List<PlatformTree> platformTrees = treeDao.findByPid(platname,p.getId());
                deletetreenode(platname,platformTrees,pid);
                List<Permission> permissions = permissionDao.findByPidAndNid(Integer.parseInt(pid),p.getId());
                if (permissions.size() != 0){
//                    System.out.println(permissions.get(0).getnId());
                    System.out.println("inside");
                    for(Permission s : permissions){
                        permissionDao.deletePermByAll(s.getrId(),s.getnId(),s.getpId());
                    }
                }
                treeDao.delTreeNode(platname,p.getId());
            }
        }
    }

    //遍历出整棵树（不加权限匹配）
    public Votetree getWholeTree(String table, PlatformTree platformTree){
        Votetree votetree = new Votetree(platformTree.getId(),platformTree.getCname(),true);
        List<PlatformTree> list = treeDao.findByPid(table,platformTree.getId());
        if( list.size() > 0 ){
            List<Votetree> childlist = new ArrayList<>();
            for(PlatformTree pt : list){
                    childlist.add(getWholeTree(table,pt));
            }
            votetree.setChildren(childlist);
        }
        return votetree;
    }

    //遍历整棵树（加权限匹配） table 表名， arry[]权限表
    public Votetree getWholeTree(String table, PlatformTree platformTree, List arry){
        Votetree votetree = new Votetree(platformTree.getId(),platformTree.getCname(),true);
        List<PlatformTree> list = treeDao.findByPid(table,platformTree.getId());
        if( list.size() > 0 ){
            List<Votetree> childlist = new ArrayList<>();
            for(PlatformTree pt : list){
                if(arry.contains(pt.getId())){           //判断用户是否拥有该权限
                    childlist.add(getWholeTree(table,pt,arry));
                }
            }
            votetree.setChildren(childlist);
        }
        return votetree;
    }

    // 查找 该节点 的孩子节点
    private List<PlatformTree> getDeeptLevel(String table, PlatformTree platformTree){
        List<PlatformTree> platformTreeList = new ArrayList<>();
        platformTreeList = treeDao.findByPid(table,platformTree.getId());
        return platformTreeList;
    }

    //确定表名 + 按照id查找当前信息
    private PlatformTree getTabAndNode(String pid, String id){
        String table = "platform"+pid;
        PlatformTree p = new PlatformTree();
        p = treeDao.findById(table,Integer.parseInt(id));
        return p;
    }

    //将排序前端返回的hashmap转成platform树
    public PlatformTree hashToTree(String tableName, PlatformTree tree, HashMap hashMap) {
        List<PlatformTree> platformTrees = new ArrayList<>();

        List<HashMap> list = (List<HashMap>)hashMap.get("children");
        for(HashMap h : list) {
            PlatformTree plat = treeDao.findById(tableName, (Integer) h.get("id"));
            plat.setPid(tree.getId());
            platformTrees.add(hashToTree(tableName, plat, h));
        }
        tree.setChildren(platformTrees);

        return tree;
    }

    //将数据写进数据库
    public void writeToMyql(String tableName, PlatformTree tree) {
        treeDao.addTreeNode(tableName, tree.getId(), tree.getCname(), tree.getEname(), tree.getPid(),
                tree.getNotes(), tree.getTag());
        List<PlatformTree> treeList = tree.getChildren();
        if(treeList.size() > 0) {
            for (PlatformTree tr : treeList) {
                writeToMyql(tableName, tr);
            }
        }
    }
}
