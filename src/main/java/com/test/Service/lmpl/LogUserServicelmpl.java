package com.test.Service.lmpl;

import com.test.Dao.LogUserDao;
import com.test.Entity.User;
import com.test.Service.LogUserService;
import com.test.tool.MD5;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LogUserServicelmpl implements LogUserService {
    @Resource
    private LogUserDao logUserDao;

    @Override
    public String Login(String uid, String upwd,String ip) {
        System.out.println("uacc ="+uid+"   upwd="+upwd);
        if ( (uid == null || uid.equals("")) || (upwd == null || upwd.equals("")) ){
            return "账号或密码不可为空！";
        }
        User userinfor = new User();                 //存放该用户的数据库信息
        userinfor = logUserDao.findById(Integer.parseInt(uid));

        String passwd = MD5.encode(upwd);
        if(userinfor == null){
            return "该用户不存在！请先注册！";
        }
        if (!(passwd.equals( userinfor.getUpwd() )))
        {
            return "账号或密码不正确！";
        }
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date1 = sdf.format(date);
        int res = logUserDao.uplogin(date1,ip,uid);
        System.out.println("登陆成功");
        return userinfor.getSuser().toString();
    }

    @Override
    public String adduser(User user)
    {
        String passwd = MD5.encode(user.getUpwd());
        user.setUpwd(passwd);
        Date  date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date1 = sdf.format(date);        //获取当前时间
        user.setStime(date1);
        int res = logUserDao.addUser(user);
        if(res == 1){
            return String.valueOf(user.getUid());
        }
        else{
            return "添加用户失败,请重新添加！";
        }

    }

    @Override
    public String deluser(String uid) {
        int res = logUserDao.delUser(uid);
        if(res == 1){
            return "删除用户成功";
        }
        else {
            return "删除用户失败";
        }
    }

    @Override
    public String updateuser(User user) {
        user.setUpwd(MD5.encode(user.getUpwd()));;
        int res = logUserDao.update(user);
        if(res == 1){
            return "更改用户信息成功";
        }
        else{
            return "更改用户信息失败";
        }
    }

    @Override
    public User findById(String uid) {
        User user = logUserDao.findById(Integer.valueOf(uid));
        if(user == null){
            return null;
        }
        else {
            return user;
        }
    }


    @Override
    //返回所有的用户信息
    public List<User> findAllUsers() {
        return logUserDao.findAllUsers();
    }

    @Override
    public String changestate(String uid) {
        int res = logUserDao.updateUstate(1,Integer.valueOf(uid));
        if(res == 1){
            return "success";
        }
        else
        {
            return "fail";
        }
    }

    @Override
    public String changeroleid(String uid, String roleid) {
        int res = logUserDao.updateRoleId(Integer.parseInt(roleid),Integer.parseInt(uid));
        if(res == 1){
            return "success";
        }else {
            return "fail";
        }
    }

//    @Override
//    public String creat() {
//        int res = logUserDao.creattable("table_data");
//        if(res == 1){
//            return "成功";
//        }
//        else {
//            return "失败";
//        }
//    }
}
