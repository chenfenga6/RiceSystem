package com.test.Dao;

import com.test.Entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionDao {
    /*******************************************Role表**********************************/
    //通过rname 查找 rid
    @Select("select rid from role where rname=#{arg0}")
    int findRidByRname(String rname);
    //通过rname 获取角色全部信息
    @Select("select * from role where rname=#{arg0}")
    Role findByRname(String rname);
    //插入角色
    @Insert("insert into role(rname,notes) values(#{arg0},#{arg1})")
    int addRole(String rname,String notes);
    //查找所有角色信息
    @Select("select * from role")
    List<Role> findAllRoles();

    /****************************************Permission表**********************************/
    //插入权限节点
    @Insert("insert into permission(rId,nId,pId) values(#{arg0},#{arg1},#{arg2})")
    int addPermission(Integer rid,Integer nid,Integer pid);
}
