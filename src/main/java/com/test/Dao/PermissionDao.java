package com.test.Dao;

import com.test.Entity.Role;
import org.apache.ibatis.annotations.*;

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

    //修改角色信息
    @Update("update role set rname=#{arg1},notes=#{arg2} where rid=#{arg0}")
    int changeRole(Integer rid,String rname,String notes);

    //删除角色信息
    @Delete("delete from role where rid=#{arg0}")
    int deleteRole(Integer rid);

    /****************************************Permission表**********************************/
    //查询 角色为rid 平台为pid 的权限条数
    @Select("select count(*) from permission where rid=#{arg0} and pid=#{arg1}")
    int countPermByRandP(Integer rid,Integer pid);

    //插入权限节点
    @Insert("insert into permission(rId,nId,pId) values(#{arg0},#{arg1},#{arg2})")
    int addPermission(Integer rid,Integer nid,Integer pid);

    //删除结点（通过rid和pid）
    @Delete("delete from role where rid=#{arg0} and pid=#{arg1}")
    int deletePermByRAndP(Integer rid,Integer pid);
    //删除结点（通过nid)
    @Delete("delete from role where nid=#{arg0}")
    int deletePermByNid(Integer nid);
}