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
    public String addRole(String rname) {
        if(permissionDao.findByRname(rname) != null){
            return "该角色已存在";
        }
        int ret = permissionDao.addRole(rname,null);
        if(ret == 1){
            return "success";
        }
        return "fail";
    }

    @Override
    public String addPermission(Integer rid, Integer pid, List<Votetree> list) {
//        System.out.println("rid="+rid+" pid="+pid);
//        System.out.println("共有结点"+list.size()+"个");
        traverseTree(list.get(0),rid,pid);
        return "success";
    }

    @Override
    public List<Role> findAllRoles() {
        return permissionDao.findAllRoles();
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
