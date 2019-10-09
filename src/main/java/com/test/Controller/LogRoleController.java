package com.test.Controller;

import com.test.Entity.Permissiontree;
import com.test.Entity.Resdata;
import com.test.Entity.Role;
import com.test.Service.LogRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/permission")
public class LogRoleController {
    @Autowired
    LogRoleService logRoleService;

    //增加角色
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public String addRole(@RequestParam String rname,@RequestParam String notes){
        System.out.println("增加角色："+rname);
        return logRoleService.addRole(rname,notes);
    }

    //增加某角色的某平台权限
    @RequestMapping(value = "/addPermission",method = RequestMethod.POST)
    public String addPermission(@RequestParam Integer rid,@RequestParam Integer pid, @RequestBody List<Permissiontree> list){
        System.out.println("改动角色"+rid+" 的平台"+pid+" 权限");
        return logRoleService.addPermission(rid,pid,list);
    }

    //显示当前所有角色
    @RequestMapping(value = "/findAllRoles",method = RequestMethod.POST)
    public List<Role> findAllRoles(){
        System.out.println("显示当前所有角色！");
        return logRoleService.findAllRoles();
    }

    //修改角色信息
    @RequestMapping(value = "/changeRole",method = RequestMethod.POST)
    public String changeRole(@RequestParam Integer rid,@RequestParam String rname,@RequestParam String notes){
        System.out.println("修改角色："+rid+" 信息");
        return logRoleService.changeRole(rid,rname,notes);
    }

    //删除角色信息
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    public String deleteRole(@RequestParam Integer rid){
        System.out.println("删除角色："+rid);
        return logRoleService.deleteRole(rid);
    }

    //查看 某角色 某平台 已分配的权限
    @RequestMapping(value = "/findPermByRAndP",method = RequestMethod.POST)
    public Resdata findPermByRAndP(@RequestParam Integer rid, @RequestParam Integer pid ){
        System.out.println("查看角色"+rid+" 平台 "+pid+" 已分配的权限");
        return logRoleService.findPermByRAndP(rid,pid);
    }

    //根据Rid查找角色信息
    @RequestMapping(value = "/findRoleByRid",method  = RequestMethod.POST)
    public String findRoleByRid(@RequestParam Integer rid){
        System.out.println("查找角色信息:"+rid);
        return logRoleService.findRoleByRid(rid);
    }

}
