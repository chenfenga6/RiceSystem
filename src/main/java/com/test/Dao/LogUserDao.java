package com.test.Dao;

import com.test.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogUserDao {
    @Select("select * from user_data") //查找所有用户
    List<User> findAllUsers();

    @Select("select * from user_data where uid=#{uid}")     //通过id找用户
    User findById(Integer uid);

    //增加普通用户
    @Insert("insert into user_data (uname,upwd,spermission,mpermission,ustate,mail,company,suser,stime,roleId)" +
            " values (#{uname},#{upwd},#{spermission},#{mpermission},#{ustate},#{mail},#{company},#{suser},#{stime},#{roleId})")
    @Options(useGeneratedKeys=true, keyProperty="uid", keyColumn="uid")
    int addUser(User user);

//    //增加超级用户
//    @Insert("insert into user_data (uname,upwd,suser,stime) values (#{uname},#{upwd},#{suser},#{stime})")
//    @Options(useGeneratedKeys=true, keyProperty="uid", keyColumn="uid")
//    int addSuser(User user);

    //删除用户
    @Delete("delete from user_data where uid = #{arg0}")
    int delUser(String uid);

    //更改用户信息
    @Update("update user_data set uname=#{uname},upwd=#{upwd}," +
            "spermission=#{spermission},mpermission=#{mpermission},mail=#{mail},company=#{company}" +
            " where uid=#{uid}")
    int update(User user);

    //更新登陆时间和登陆ip
    @Update("update user_data set btime=#{arg0},bip=#{arg1} where uid=#{arg2}")
    int uplogin(String btime,String bip,String uid);

    //查找某角色下的用户
    @Select("select * from user_data where roleId=#{arg0}")
    List<User> findByrId(Integer rid);

    //更改角色信息
    @Update("update user_data set roleId=#{arg0} where uid=#{arg1}")
    int updateRid(Integer rid,Integer uid);

    //更改用户审核状态
    @Update("update user_data set ustate=#{arg0} where uid=#{arg1}")
    int updateUstate(Integer ustate,Integer uid);

    //更改用户角色权限
    @Update("update user_data set roleId=#{arg0} where uid=#{arg1}")
    int updateRoleId(Integer roleId,Integer uid);


//    @Update("create table ${tablename} (id int(10) KEY NOT NULL AUTO_INCREMENT, " +
//            "name varchar(10))ENGINE=InnoDB DEFAULT CHARSET=utf8")
//    int creattable(@Param(value = "tablename") String tablename)throws DataAccessException;

}
