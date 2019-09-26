package com.test.Service;

import com.test.Entity.User;

public interface UserService {
    String Login(String uid,String upwd);
    String adduser(User user);
    String deluser(String uid);
    String updateuser(User user);
    User findById(String uid);
}
