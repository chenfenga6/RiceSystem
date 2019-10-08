package com.test.Service;

import com.test.Entity.User;

import java.util.List;

public interface UserService {
    // String Login(String uid, String upwd);

    String adduser(User user);

    String deluser(String uid);

    String updateuser(User user);

    User findById(String uid);

    String Login(String uid,String upwd,String ip);

    List<User> findAllUsers();

    String changestate(String uid);

    String changeroleid(String uid,String roleid);

//    String creat();
}
