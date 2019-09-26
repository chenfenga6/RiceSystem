package com.test.Dao;

import com.test.Entity.Platform;
import com.test.Entity.PlatformTree;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlatformDao {

    //新增平台
    @Insert("insert into platform_data(pname, pcode, purl, plog) values(#{arg0}, #{arg1}, #{arg2}, #{arg3})")
    int insertPlatform(String pname, String pcode, String purl, String plog);

    //新建表
    @Update("create table ${tableName} (" +
            "id int(11) NOT NULL AUTO_INCREMENT," +
            "cname varchar(255) NOT NULL," +
            "ename varchar(255) DEFAULT NULL," +
            "pid int(11) NOT NULL," +
            "notes varchar(255) DEFAULT NULL," +
            "tag varchar(255) DEFAULT NULL," +
            "PRIMARY KEY (id)) " +
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
    int getLastPid();

    //查找某平台（通过 pname）
    @Select("select * from platform_data where pname=#{pname}")
    Platform findByPname(String pname);

    //查看所有平台信息
    @Select("select * from platform_data")
    List<Platform> findAll();


    @Select("select * from ${arg0} where pid=#{arg1}")
    List<PlatformTree> findByTreePid(String table, Integer pid);   //通过Pid查找

    @Select("select * from ${arg0} where id=#{arg1}")
    PlatformTree findByTreeId(String table, Integer id);   //通过id查找

}
