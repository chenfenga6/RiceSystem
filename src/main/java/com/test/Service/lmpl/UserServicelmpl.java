package com.test.Service.lmpl;

import com.test.Dao.UserDao;
import com.test.Entity.User;
import com.test.Service.UserService;
import com.test.tool.MD5;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServicelmpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public String Login(String uid, String upwd) {
        System.out.println("uacc ="+uid+"   upwd="+upwd);
        if ( (uid == null || uid.equals("")) || (upwd == null || upwd.equals("")) ){
            return "账号或密码不可为空！";
        }
        User userinfor = new User();                 //存放该用户的数据库信息
        userinfor = userDao.findById(Integer.parseInt(uid));

        if(userinfor == null){
            return "该用户不存在！请先注册！";
        }
        if (!(upwd.equals( userinfor.getUpwd() )))
        {
            return "账号或密码不正确！";
        }
        return "登陆成功！";
    }

    @Override
    public String adduser(User user) {
        String passwd = MD5.encode(user.getUpwd());
        user.setUpwd(passwd);
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date1 = sdf.format(date);        //获取当前时间
        user.setStime(date1);
        int res = userDao.addUser(user);
        if(res == 1){
            return String.valueOf(user.getUid());
        }
        else{
            return "添加用户失败,请重新添加！";
        }
    }

    @Override
    public String deluser(String uid) {
        int res = userDao.delUser(uid);
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
        int res = userDao.update(user);
        if(res == 1){
            return "更改用户信息成功";
        }
        else{
            return "更改用户信息失败";
        }
    }

    @Override
    public User findById(String uid) {
        User user = userDao.findById(Integer.valueOf(uid));
        if(user == null){
            return null;
        }
        else {
            return user;
        }
    }
}
