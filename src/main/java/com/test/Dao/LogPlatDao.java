package com.test.Dao;

import com.test.Entity.Platform;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogPlatDao {

    //新增平台
    @Insert("insert into platform_data(pname, pcode, purl, plog) values(#{arg0}, #{arg1}, #{arg2}, #{arg3})")
    int insertPlatform(String pname, String pcode, String purl, String plog);

    //新建表
    @Update("create table ${tableName} (" +
            "id int(11) NOT NULL," +
            "cname varchar(255) NOT NULL," +
            "ename varchar(255) DEFAULT NULL," +
            "pid int(11) NOT NULL," +
            "notes varchar(255) DEFAULT NULL," +
            "tag varchar(255) DEFAULT NULL ) " +
            "ENGINE=InnoDB DEFAULT CHARSET=utf8")
    int createTab(@Param("tableName") String tableName);

    //删除平台
    @Delete("delete from platform_data where pid = #{pid}")
    int deletePlatform(Integer pid);

    //删除表
    @Update("drop table if exists ${tableName}")
    int deleteTab(@Param("tableName") String tableName);

    // 修改平台
    @Update("update platform_data set pname = #{arg1}, pcode = #{arg2}, purl = #{arg3}, plog = #{arg4} where pid = #{arg0}")
    int updatePlatform(Integer pid, String pname, String pcode, String purl, String plog);

    //查找某平台
    @Select("select * from platform_data where pid = #{pid}")
    Platform findByPid(Integer pid);

    //查找最新的pid
    @Select("select max(pid) from platform_data")
    int getLatestPid();

    //查看所有平台信息
    @Select("select * from platform_data")
    List<Platform> findAll();

    //删除permission表中某平台所有结点（通过pid)
    @Delete("delete from permission where pId = #{Pid}")
    int deletePermByPid(Integer Pid);

    //更新平台表里根结点名字
    @Update("update ${arg0} set cname=#{arg1} where id =#{arg2}")
    int updateNodeName(String table, String newName, Integer id);

    //新增 table 表根节点信息
    @Insert("insert into ${arg0}(id,cname,ename,pid,notes,tag) values(#{arg1},#{arg2},#{arg3},#{arg4},#{arg5},#{arg6})")
    int addTreeNode(String table, Integer id, String cname, String ename, Integer pid, String notes, String tag);
}
