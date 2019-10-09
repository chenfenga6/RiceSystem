package com.test;
import com.test.Dao.SysFunDao;
import com.test.Entity.Permission;
import com.test.Entity.Permissiontree;
import com.test.Entity.PlatformTree;
import com.test.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试专用  在项目中无重要意义
 */
public class test {

    public static void main(String args[]){
        SysFunDao sy = null;
        PlatformTree ptree = new PlatformTree();
        ptree =sy.findById("platform57",1);
        System.out.println(ptree.getCname());
    }
}
