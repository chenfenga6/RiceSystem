package com.test.Service;

import com.test.Entity.Role;
import com.test.Entity.Votetree;

import java.util.List;

public interface PermissionService {
    //增加角色
    String addRole(String rname);
    //增加权限
    String addPermission(Integer rid, Integer pid, List<Votetree> list);
    //显示当前所有角色
    List<Role> findAllRoles();
}
