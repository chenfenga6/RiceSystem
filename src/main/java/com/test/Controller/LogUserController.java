package com.test.Controller;

import com.test.Entity.User;
import com.test.Service.LogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class LogUserController {
    @Autowired
    protected LogUserService logUserService;

    /**
     * 管理员创建用户
     * @param user 用户结构体
     * @return  成功：返回“sucess”,失败：返回原因
     */

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String Useradd(@RequestBody User user){
        return logUserService.adduser(user);
    }

    /**
     * 管理员删除用户
     * @param uid
     * @return 成功：返回“sucess”,失败：返回原因
     */
    @RequestMapping(value = "/sub",method = RequestMethod.POST)
    @ResponseBody
    public String Userdel(@RequestParam String uid)
    {
        return logUserService.deluser(uid);
    }

    /**
     * 管理员修改用户信息
     * @body user
     *
     */
    @RequestMapping(value = "/cha",method = RequestMethod.POST)
    @ResponseBody
    public String Userchange(@RequestBody User user){
        return logUserService.updateuser(user);
    }

    /**
     * 查找某用户所有信息
     * @param  uid
     */
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    @ResponseBody
    public User Userfind(@RequestParam String uid){
        return logUserService.findById(uid);
    }

    /**
     * 用户登陆接口
     * @param uid     账号
     * @param upwd     密码
     * @return         "账号或密码不可为空！"/"该用户不存在！请先注册！"/"账号或密码不正确！"/"success"
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String Login(@RequestParam String uid, @RequestParam String upwd,@RequestParam String ip){
        return logUserService.Login(uid,upwd,ip);
    }

    //查找所有用户
    @RequestMapping(value = "/findAllUsers",method = RequestMethod.POST)
    public List<User> findAllUsers(){
        return logUserService.findAllUsers();
    }

    //更改用户审核状态
    @RequestMapping(value = "/changState",method = RequestMethod.POST)
    @ResponseBody
    public String ChangeState(@RequestParam String uid){
        return logUserService.changestate(uid);
    }

    //更改用户角色权限
    @RequestMapping(value = "/changRoleId",method = RequestMethod.POST)
    @ResponseBody
    public String ChangeRoleId(@RequestParam String uid,@RequestParam String roleid){
        return logUserService.changeroleid(uid,roleid);
    }

}
