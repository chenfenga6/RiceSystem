package com.test.Controller;

import com.test.Entity.Role;
import com.test.Entity.Votetree;
import com.test.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public String addRole(@RequestParam String rname){
        System.out.println("增加角色："+rname);
        return permissionService.addRole(rname);
    }

    @RequestMapping(value = "/addPermission",method = RequestMethod.POST)
    public String addPermission(@RequestParam Integer rid,@RequestParam Integer pid, @RequestBody List<Votetree> list){
        return permissionService.addPermission(rid,pid,list);
    }

    @RequestMapping(value = "/findAllRoles",method = RequestMethod.POST)
    public List<Role> findAllRoles(){
        return permissionService.findAllRoles();
    }
}
