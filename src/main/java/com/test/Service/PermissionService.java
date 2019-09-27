package com.test.Service;

import com.test.Entity.Role;
import com.test.Entity.Votetree;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface PermissionService {
    /********************************role表***********************************/
    //增加角色
    String addRole(String rname,String notes);
    //显示当前所有角色
    List<Role> findAllRoles();
    //修改角色信息
    String changeRole(Integer rid,String rname,String notes);
    //删除角色信息
    String deleteRole(Integer rid);

    /********************************Permission表***********************************/
    //增加某角色的某平台权限
    String addPermission(Integer rid, Integer pid, List<Votetree> list);
    //查看 某角色 某平台 已分配的权限
    String findPermByRAndP(Integer rid,Integer pid);
}
