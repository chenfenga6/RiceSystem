layui.use(['layer','form','laypage'], function(){
    var layer = layui.layer,
        form = layui.form;

    $(document).ready(function () {
        var User = function () {
        };
        var roleId = '';
        User.prototype = {
            //添加用户弹出框
            popFunc: function () {
                /*
                   功能：获取所有角色信息
                   params：无
                   result：所有角色信息
                 */
                axios({
                    url:BASE_URL+'/permission/findAllRoles',
                    method:'POST',
                    params:{},
                    contentType:"application/json",
                    headers:{
                        'Content-Type':'application/json'
                    }
                }).then((res)=> {
                    console.log("****获取所有角色信息****");
                    console.log(res.data);
                    console.log("***********************");
                    let allRoleInfo = res.data;

                    // 下拉框插入选项
                    for(let i=0; i < allRoleInfo.length; i++){
                        let data='<option>'+allRoleInfo[i].rname +'</option>';
                        $('#opType').append(data);
                    }
                    layer.open({
                        type:1,
                        title:"添加用户信息",
                        area:["40%"],
                        skin:"addUser",
                        content:$("#userTable").html()
                    });
                    form.render('select');
                });
            },
            //获取和角色选择下拉框信息匹配的角色id
            getRoleId: function(data) {
                console.log("****获取输入框内信息****");
                console.log(data.field);
                console.log("***********************");

                /*
                    功能：获取所有角色信息
                    params：无
                    result：所有角色信息
                 */
                axios({
                    url:BASE_URL+'/permission/findAllRoles',
                    method:'POST',
                    params:{},
                }).then((res)=> {
                    console.log("****获取所有角色信息****");
                    console.log(res.data);
                    console.log("***********************");
                    let allRoleInfo = res.data;

                    // 将填入的角色名与所有角色信息进行匹配，若未填写，默认为下拉框第一个角色名
                    for (let i = 0; i < allRoleInfo.length; i++) {
                        if (data.field.orgType === null) {
                            roleId = allRoleInfo[0].rid;
                        } else if (allRoleInfo[i].rname === data.field.orgType) {
                            roleId = allRoleInfo[i].rid;
                        }
                    }
                    /*
                        调用用户添加函数
                        data：输入框内填写的数据
                     */
                    this.userAdd(data);
                });
            },
            //获取当前角色名并显示所有数据
            getUserInfo: function (id) {
                console.log("****获取当前用户id****");
                console.log(id);
                console.log("*********************");

                /*
                    功能：查找某指定用户的所有信息
                    params：id 用户id
                    result：该用户所有信息
                 */
                axios({
                    url:BASE_URL+'/user/find',
                    method:'POST',
                    params:{
                        uid:id,
                    },
                }).then((res)=> {
                    /*
                        调用用户编辑函数
                        res.data：某用户所有信息
                        id：当前用户id
                     */
                    this.userEdit(res.data, id);
                });
            },
            //获取所有用户信息
            getRoleInfo: function () {
                /*
                    功能：获取所有角色信息
                    params：无
                    result：所有角色信息
                 */
                axios({
                    url:BASE_URL+'/permission/findAllRoles',
                    method:'POST',
                    params:{},
                }).then((res)=> {
                    /*
                        调用用户管理函数
                        res.data：所有角色信息
                     */
                    this.userManage(res.data);
                });
            },
            //用户管理界面
            userManage:function(roleInfo){
                /*
                    功能：获取所有用户信息
                    params：无
                    result：所有用户信息
                 */
                axios({
                    url:BASE_URL+'/user/findAllUsers',
                    method:'POST',
                    params:{},
                }).then((res)=> {

                    console.log("****获取所有用户信息****");
                    console.log(res.data);
                    console.log("**********************");
                    let allUserInfo=res.data;
                    let roleName = "";

                    for(let i=0; i<allUserInfo.length;i++){
                        for (let j=0; j<roleInfo.length;j++) {
                            if (allUserInfo[i].roleId === roleInfo[j].rid) {
                                roleName = roleInfo[j].rname;
                            }
                        }

                        let popContent =
                            '<tr>\n' +
                            '                    <td>'+i+'</td>\n' +
                            '                    <td>'+allUserInfo[i].uid+'</td>\n' +
                            '                    <td>'+allUserInfo[i].uname+'</td>\n' +
                            '                    <td>'+allUserInfo[i].company+'</td>\n' +
                            '                    <td>'+allUserInfo[i].mail+'</td>\n' +
                            '                    <td>'+allUserInfo[i].stime+'</td>\n' +
                            '                    <td>'+allUserInfo[i].bip +'</td>\n' +
                            '                    <td>'+allUserInfo[i].btime +'</td>\n' +
                            '                    <td>'+roleName +'</td>' +
                            '                     <td>\n' +
                            '                        <button class="layui-btn layui-btn-xs UserEdit" name="'+allUserInfo[i].uid+'">编辑</button>\n' +
                            '                        <button class="layui-btn  layui-btn-danger layui-btn-xs UserDelete" name="'+allUserInfo[i].uid+'" >删除</button>\n' +
                            '                    </td>\n' +
                            '                </tr>';
                        $('#tableBody').append(popContent);
                    }
                });
            },
            //添加用户
            userAdd: function (data) {
                /*
                    需要传给后端的数据
                    uname:用户名
                    upwd：密码
                    mail：邮箱
                    company：公司
                    roleId：角色id
                    suser：
                    ustate：审核状态
                 */
                var addUserInfo = {
                    uname:data.field.uname,
                    upwd:data.field.upwd,
                    mail:data.field.mail,
                    company:data.field.company,
                    roleId:roleId,
                    suser:0,
                    ustate:"0",
                };

                // 提交给后端
                axios({
                    url:BASE_URL+'/user/add',
                    method:'POST',
                    data: addUserInfo
                }).then((res)=> {
                    // alert(res.data);
                    location.reload();
                });
                // return true;
            },
            //删除用户
            userDelete: function (id) {
                /*
                    功能：删除某个用户
                    params：用户id
                    result：无
                 */
                axios({
                    url:BASE_URL+'/user/sub',
                    method:'POST',
                    params:{
                        uid:id,
                    },
                }).then((res)=> {
                    console.log(res.data);
                    layui.use(['layer'], function(){
                        let layer=layui.layer;
                        layer.confirm('删除不可恢复，是否删除？', {icon: 3, title:'提示'}, function(index){
                            //将要删除的平台id发送给后端。
                            layer.close(index);
                            location.reload();
                        });
                    });
                });
            },
            //编辑用户
            userEdit:function(userInfo, id) {
                layui.use(['layer','form','laypage'], function() {
                    let layer = layui.layer;
                    let form = layui.form;
                    var str = {};
                    /*
                    功能：获取所有角色信息
                    params：无
                    result：所有角色信息
                 */
                    axios({
                        url:BASE_URL+'/permission/findAllRoles',
                        method:'POST',
                        params:{},
                    }).then((res)=> {

                        $('#opType1').html('');   //清空
                        let allRoleInfo = res.data;
                        for(let i=0; i < allRoleInfo.length; i++){
                            let data='<option>'+allRoleInfo[i].rname +'</option>';
                            if (userInfo.roleId === allRoleInfo[i].rid) {
                                $('#opType1').append(data);
                            }
                        }
                        for(let i=0; i < allRoleInfo.length; i++){
                            let data='<option>'+allRoleInfo[i].rname +'</option>';
                            if (userInfo.roleId !== allRoleInfo[i].rid) {
                                $('#opType1').append(data);
                            }
                        }

                        layer.open({
                            type: 1,
                            title: "编辑角色",
                            area: ["40%"],
                            skin: "editUser",
                            content: $("#userTable1").html()
                        });
                        form.render('select');
                        //传用户的id给后端  从后端取当前平台的基本信息，

                        var data={
                            company:userInfo.company,
                            mail:userInfo.mail,
                            uname:userInfo.uname
                        };
                        // 将从后端获取到的数据填入输入框，显示该用户原有数据
                        form.val("editUser", {
                            "company":data.company,
                            "mail":data.mail,
                            "uname":data.uname
                        });

                        // 提交至后端
                        form.on('submit(editUser)', function (data) {
                            console.log("****获取输入框内信息****");
                            console.log(data.field);
                            console.log("***********************");
                            for (let i = 0; i < allRoleInfo.length; i++) {
                                if (allRoleInfo[i].rname === data.field.orgType) {
                                    roleId = allRoleInfo[i].rid;
                                }
                            }

                            // 需要传给后端的数据
                            editUserInfo = {
                                uid:id,
                                uname:data.field.uname,
                                mail:data.field.mail,
                                company:data.field.company,
                                roleId:roleId,
                                suser:0,
                                ustate:"0",
                            };

                            axios({
                                url:BASE_URL+'/user/cha',
                                method:'POST',
                                data:editUserInfo,
                            }).then((res)=> {
                                // alert(res.data);
                                location.reload();
                            });
                            return false;
                        });
                    });
                });
            },
        };

        var user = new User();
        //获取角色信息
        user.getRoleInfo();

        //触发添加用户功能
        $(document).on("click", ".AddUser", function () {
            user.popFunc();
            form.on('submit(addUser)', function(data){
                user.getRoleId(data); //获取角色Id
            });
        });

        //触发修改用户功能
        $(document).on("click",".UserEdit",function(){
            // console.log(this.name);  //当前点击用户的id
            user.getUserInfo(this.name);
        });

        //触发删除用户功能
        $(document).on("click",".UserDelete",function(){
            // console.log(this.name);  //当前点击平台的id
            user.userDelete(this.name);
        });
    });
});