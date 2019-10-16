package com.test.Dao;

import com.test.Entity.Permission;
import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysFunDao {
    //通过Id查找节点
    @Select("select * from ${arg0} where id=#{arg1}")
    PlatformTree findById(String table,Integer id);

    //通过Cname查找节点
    @Select("select * from ${arg0} where cname=#{arg1}")
    PlatformTree findByCname(String table,String cName);

    //通过Pid查找节点
    @Select("select * from ${arg0} where pid=#{arg1}")
    List<PlatformTree> findByPid(String table, Integer pid);

    //查找所有平台的根节点
    @Select("select * from platform_data")
    List<Platform> findAllPlatformPid();

    //修改 table表 节点信息
    @Update("update ${arg0} set cname=#{arg2},ename=#{arg3},pid=#{arg4},notes=#{arg5},tag=#{arg6} where id = #{arg1}")
    int changeTreeNode(String table,Integer id,String cname,String eName,Integer pid,String notes,String tag);

    //删除 table表 节点信息
    @Delete("delete from ${arg0} where id=#{arg1}")
    int delTreeNode(String table,Integer id);


    //查找最新的id
    @Select("select max(id) from ${tableName}")
    int getMaxId(@Param("tableName") String tableName);

    //删除表里所有节点
    @Delete("delete from ${table} where 1=1")
    int deleteTable(@Param("table") String table);

    //将排序后的树写入数据库
    @Insert("insert into ${arg0}(id,cname,ename,pid,notes,tag) values(#{arg1},#{arg2},#{arg3},#{arg4},#{arg5},#{arg6})")
    int addTreeNode(String table, Integer id, String cName, String eName,
                 Integer pid, String notes, String tag);

    @Update("update ${arg0} set cname=#{arg1},ename=#{arg2},notes=#{arg3},tag=#{arg4} where id=#{arg5}")
    int updateWithoutIdAndPid(String table,String cname,String ename,String notes,String tag,int targetId);
    /*===============================权限测试===================================================*/
    @Insert("insert into permission(rId,nId,pId) values(#{arg0},#{arg1},#{arg2})")
    int addPermission(Integer rid,Integer nid,Integer pid);

    @Select("select * from permission where rId=#{arg0} and pId=#{arg1}")
    List<Permission> findPermissionByRidAndPid(Integer rid,Integer pid);
}
