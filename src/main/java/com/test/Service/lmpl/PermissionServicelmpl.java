package com.test.Service.lmpl;

import com.alibaba.fastjson.JSON;
import com.test.Dao.PermissionDao;
import com.test.Entity.Role;
import com.test.Entity.Votetree;
import com.test.Service.PermissionService;
import com.test.Service.TreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServicelmpl implements PermissionService {
    @Resource
    PermissionDao permissionDao;

    @Override
    //增加角色
    public String addRole(String rname,String notes) {
        if(permissionDao.findByRname(rname) != null){
            return "该角色已存在";
        }
        int ret = permissionDao.addRole(rname,notes);
        if(ret == 1){
            return "success";
        }
        return "fail";
    }

    @Override
    //显示当前所有角色
    public List<Role> findAllRoles() {
        return permissionDao.findAllRoles();
    }

    @Override
    //修改角色信息
    public String changeRole(Integer rid, String rname, String notes) {
        int ret = permissionDao.changeRole(rid,rname,notes);
        if(ret==1){
            return "success";
        }
        return "faile";
    }

    @Override
    //删除角色信息
    public String deleteRole(Integer rid) {
        int ret = permissionDao.deleteRole(rid);
        if(ret == 1){
            return "success";
        }
        return "faile";
    }

    @Override
    //增加某角色的某平台权限
    public String addPermission(Integer rid, Integer pid, List<Votetree> list) {
//        System.out.println("rid="+rid+" pid="+pid);
//        System.out.println("共有结点"+list.size()+"个");
        traverseTree(list.get(0),rid,pid);
        return "success";
    }

    @Override
    //查看 某角色 某平台 已分配的权限
    public String findPermByRAndP(Integer rid, Integer pid) {
        System.out.println("rid="+rid+" and pid="+pid+" 共有"+permissionDao.countPermByRandP(rid,pid)+"条");
        return null;
    }

    /*****************************************方法**********************************************/
    //遍历 List <Votetree> 并存储权限结点
    public void traverseTree(Votetree rootTree,Integer rid,Integer pid){
        permissionDao.addPermission(rid,rootTree.getId(),pid);
        List<Votetree> childlist = rootTree.getChildren();
        if(childlist.size() > 0 ){
            for(int i = 0 ; i < childlist.size() ; i++){
                Votetree votetree = JSON.parseObject(JSON.toJSONString(childlist.get(i)),Votetree.class);
                traverseTree(votetree,rid,pid);
            }
        }
    }
}
