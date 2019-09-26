package com.test.Dao;

import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TreeDao {
    //通过Id查找节点
    @Select("select * from ${arg0} where id=#{arg1}")
    PlatformTree findById(String table,Integer id);

    //通过Cname查找节点
    @Select("select * from ${arg0} where cname=#{arg1}")
    PlatformTree findByCname(String table,String cname);

    //通过Pid查找节点
    @Select("select * from ${arg0} where pid=#{arg1}")
    List<PlatformTree> findByPid(String table, Integer pid);

    //查找所有平台的根节点
    @Select("select * from platform_data")
    List<Platform> findAllPlatformPid();

    //新增 table表 节点 信息(id自增)
    @Insert("insert into ${arg0}(cname,ename,pid,notes,tag) values(#{arg1},#{arg2},#{arg3},#{arg4},#{arg5})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int addTreeNode(String table,String cname,String ename,Integer pid,String notes,String tag);

    //修改 table表 节点信息
    @Update("update ${arg0} set cname=#{arg2},ename=#{arg3},pid=#{arg4},notes=#{arg5},tag=#{arg6} where id = #{arg1}")
    int changeTreeNode(String table,Integer id,String cname,String ename,Integer pid,String notes,String tag);

    //删除 table表 节点信息
    @Delete("delete from ${arg0} where id=#{arg1}")
    int delTreeNode(String talbe,Integer id);

    @Update("update ${arg0} set cname=#{arg1} where id =#{arg2}")
    int changeTreeNodeCname(String table,String Newname,Integer id);
}
