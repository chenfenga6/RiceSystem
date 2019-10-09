package com.test.Service.lmpl;

import com.alibaba.fastjson.JSON;
import com.test.Dao.LogRoleDao;
import com.test.Dao.SysFunDao;
import com.test.Dao.LogUserDao;
import com.test.Entity.*;
import com.test.Service.LogRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogRoleServicelmpl implements LogRoleService {
    @Resource
    LogRoleDao logRoleDao;
    @Resource
    SysFunDao sysFunDao;
    @Resource
    LogUserDao logUserDao;

    @Override
    //根据rid 查找 Role
    public String findRoleByRid(Integer rid) {
        Role role = logRoleDao.findByRid(rid);
        return JSON.toJSONString(role);
    }

    @Override
    //增加角色
    public String addRole(String rname,String notes) {
        if(logRoleDao.findByRname(rname) != null){
            return "该角色已存在";
        }
        int ret = logRoleDao.addRole(rname,notes);
        if(ret == 1){
            return "success";
        }
        return "fail";
    }

    @Override
    //显示当前所有角色
    public List<Role> findAllRoles() {
        return logRoleDao.findAllRoles();
    }

    @Override
    //修改角色信息
    public String changeRole(Integer rid, String rname, String notes) {
        int ret = logRoleDao.changeRole(rid,rname,notes);
        if(ret==1){
            return "success";
        }
        return "faile";
    }

    @Override
    //删除角色信息
    public String deleteRole(Integer rid) {

        int ret = logRoleDao.deleteRole(rid);    //删除该角色
        logRoleDao.deletePermByRid(rid);         //删除该角色的所有权限

        List<User> users = logUserDao.findByrId(rid);
        for(User u : users){
            logUserDao.updateRid(null,u.getUid()); //清空所有用户的该角色
        }
        if(ret == 1){
            return "success";
        }
        return "faile";
    }

    @Override
    //增加某角色的某平台权限
    public String addPermission(Integer rid, Integer pid, List<Permissiontree> list) {
        List<Permission> isHavePerm = logRoleDao.findPermissionByRidAndPid(rid,pid);
        if(list.size() == 0){
            System.out.println("传送的是空树!");
            //还要加一个 如果Perm表中有权限  就得删除的方法
            for(Permission pn: isHavePerm){
                logRoleDao.deletePermById(pn.getId());
            }
            return "空权限！";
        }

        System.out.println("目前已有权限（"+isHavePerm.size()+"）个");
        if (isHavePerm.size() == 0){
            traverseTree(list.get(0),rid,pid);
        }
        else {
            List<Integer> before = new ArrayList<>();
            List<Integer> now = new ArrayList<>();
            for(Permission pn : isHavePerm){
                before.add(pn.getnId());
            }
            TreetoList(list.get(0),now);
            updataPerm(rid,pid,before,now);                 //修改 rid&pid 的 nid
        }
        isHavePerm = logRoleDao.findPermissionByRidAndPid(rid,pid);
        System.out.println("目前已有权限（"+isHavePerm.size()+"）个");
        return "success";
    }

    @Override
    //查看 某角色 某平台 已分配的权限
    public Resdata findPermByRAndP(Integer rid, Integer pid) {
        String table ="platform"+pid;
        PlatformTree rootree = sysFunDao.findById(table,1);

        List<Integer> arry = new ArrayList<>();
        List<Permission> list = logRoleDao.findPermissionByRidAndPid(rid,pid);
        for(Permission pn : list){
            arry.add(pn.getnId());
        }
        System.out.println("权限长度："+arry.size());
        Permissiontree ptree = getCheckTree(table,rootree,arry);        //打勾

        List<Permissiontree> last = new ArrayList<>();
        last.add(ptree);
        return new Resdata(rootree.getId(),rootree.getCname(),last);
//      return JSON.toJSONString(ptree);
    }




    /*****************************************方法**********************************************/
    //遍历 List <Permissiontree> 并存储权限结点
    private void traverseTree(Permissiontree rootTree,Integer rid,Integer pid){
//        System.out.println("权限+1");
        logRoleDao.addPermission(rid,rootTree.getId(),pid);
        List<Permissiontree> childlist = rootTree.getChildren();
        if(childlist.size() > 0 ){
            for(int i = 0 ; i < childlist.size() ; i++){
                Permissiontree permissiontree = JSON.parseObject(JSON.toJSONString(childlist.get(i)),Permissiontree.class);
                traverseTree(permissiontree,rid,pid);
            }
        }
    }

    //遍历 填充now List
    private void TreetoList(Permissiontree rootTree,List<Integer> now){
        now.add(rootTree.getId());
        List<Permissiontree> childlist = rootTree.getChildren();
        if (childlist.size() > 0 ){
            for(int i = 0 ; i < childlist.size() ; i++){
                Permissiontree ptree = JSON.parseObject(JSON.toJSONString(childlist.get(i)),Permissiontree.class);
                TreetoList(ptree,now);
            }
        }
    }

    //遍历整棵树 不打勾
    private Permissiontree getCheckTree(String table, PlatformTree platformTree){
        Permissiontree ptree = new Permissiontree(platformTree.getId(),platformTree.getCname(),true,false);
        List<PlatformTree> list = sysFunDao.findByPid(table,platformTree.getId());
        if( list.size() > 0 ){
            List<Permissiontree> childlist = new ArrayList<>();
            for(PlatformTree pt : list){
                childlist.add(getCheckTree(table,pt));
            }
            ptree.setChildren(childlist);
        }
        return ptree;
    }

    //遍历整棵树 找出权限内的叶子结点 并打勾
    public Permissiontree getCheckTree(String table, PlatformTree platformTree, List arry){
        Permissiontree ptree = new Permissiontree(platformTree.getId(),platformTree.getCname(),true,false);
        List<PlatformTree> list = sysFunDao.findByPid(table,platformTree.getId());
        if( list.size() > 0 ){
            List<Permissiontree> childlist = new ArrayList<>();
            for(PlatformTree pt : list){
                childlist.add(getCheckTree(table,pt,arry));
            }
            ptree.setChildren(childlist);
        }
        else if (arry.contains(ptree.getId())){
            ptree.setChecked(true);
        }
        return ptree;
    }

    //修改 角色（rid） 对于平台（pid） 的结点（nid）
    private void updataPerm(Integer rid, Integer pid, List<Integer> before, List<Integer> now){
        for (int i = 0 ; i < now.size() ; i++){             //增加权限
            if( ! before.contains(now.get(i))){
//                System.out.println("权限add+1");
                before.add(now.get(i));
                logRoleDao.addPermission(rid,now.get(i),pid);
            }
        }
        for (int i = 0 ; i < before.size() ; i++){          //删除结点
            if(! now.contains(before.get(i))){
//                System.out.println("权限sub-1");
                logRoleDao.deletePermByAll(rid,before.get(i),pid);
            }
        }
    }
}
