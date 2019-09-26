package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.test.Entity.User;

public class test {
    public static void main(String args[]){
        User user = new User();
        user.setUname("hello");
        String a= JSONArray.toJSONString(user);

        System.out.println(a);

        JSONObject object= JSON.parseObject(a);
        String name=(String) object.get("uname");
        String state=(String) object.get("ustate");

        System.out.println("name="+name+"state="+state);
    }
}
