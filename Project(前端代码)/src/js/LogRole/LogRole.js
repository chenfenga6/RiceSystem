layui.use(['layer','form'], function() {
    var layer = layui.layer,
        form = layui.form;

    $(document).ready(function() {

        var Sys = function () {
        };//类声明

        Sys.prototype = {
            popFunc: function () {//弹窗
                layer.open({
                    type: 1,
                    title: "添加角色",
                    area: ["40%"],
                    skin: "addRole",
                    content: $("#sysTable").html()
                });
            },
            roleAdd: function (data) {
                console.log(data.field);
                //提交给后端
                axios({
                    url: BASE_URL + '/permission/addRole',
                    method: 'POST',
                    params: {
                        rname: data.field.name,
                        notes: data.field.notes,
                    },
                    contentType: "application/json",
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then((res) => {
                    location.reload();
                });
            },
            roleDelete: function (id) {
                axios({
                    url:BASE_URL+'/permission/deleteRole',
                    method:'POST',
                    params:{
                        rid:id,
                    },
                    contentType:"application/json",
                    headers:{
                        'Content-Type':'application/json'
                    }
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
            roleDetail: function (id) {
                localStorage.removeItem("RoleDetail_Id");
                localStorage.setItem("RoleDetail_Id", id);
                window.location.href="./LogAuthority.html";
            },
            roleEdit: function (id) {
                axios({
                    url:BASE_URL+'/permission/findRoleByRid',
                    method:'POST',
                    params:{
                        rid:id,
                    },
                    contentType:"application/json",
                    headers:{
                        'Content-Type':'application/json'
                    }
                }).then((res)=> {
                    console.log(res.data);
                    layui.use(['layer','form','laypage'], function() {
                        let layer = layui.layer;
                        let form = layui.form;

                        layer.open({
                            type: 1,
                            title: "编辑角色",
                            area: ["40%"],
                            skin: "addRole",
                            content: $("#sysTable").html()
                        });

                        //
                        //
                        //传平台的id给后端  从后端取当前平台的基本信息，
                        //
                        //

                        var obj = JSON.parse(res.data);
                        console.log(obj.toString());
                        let data={
                            rid:obj.rid,
                            notes:obj.notes,
                            name:obj.rname,
                        };
                        console.log(data);

                        form.val("addRole", {
                            "name": data.name,
                            "notes": data.notes,
                        });

                        form.on('submit(addRole)', function (data) {

                            axios({
                                url:BASE_URL+'/permission/changeRole',
                                method:'POST',
                                params:{
                                    rid:obj.rid,
                                    rname:data.field.name,
                                    notes:data.field.notes,
                                },
                                contentType:"application/json",
                                headers:{
                                    'Content-Type':'application/json'
                                }
                            }).then((res)=> {
                                alert(res.data);
                            });
                            //提交给后端
                            return true;
                        });
                    });
                });

            },
            roleManage: function () {
                axios({
                    url: BASE_URL + '/permission/findAllRoles',
                    method: 'POST',
                    params: {},
                    contentType: "application/json",
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then((res) => {

                    console.log(res.data);
                    let tableData = res.data;
                    for (let i = 0; i < tableData.length; i++) {
                        let popContent =
                            '<tr>\n' +
                            '                    <td>' + i+ '</td>\n' +
                            '                    <td>' + tableData[i].rname + '</td>\n' +
                            '                    <td>' + tableData[i].notes + '</td>\n' +
                            '                     <td>\n' +
                            '                        <button class="layui-btn layui-btn-xs RoleEdit" name="' + tableData[i].rid + '">编辑</button>\n' +
                            '                        <button class="layui-btn  layui-btn-danger layui-btn-xs RoleDelete" name="' + tableData[i].rid + '" >删除</button>\n' +
                            '                        <button class="layui-btn  layui-btn-warm layui-btn-xs RoleDetail" name="' + tableData[i].rid + '">权限分配</button>\n' +
                            '                    </td>\n' +
                            '                </tr>';
                        $('#tableBody').append(popContent);
                    }

                });


            }
        };
        //全局方法

        var sys = new Sys();
        sys.roleManage();

        $(document).on("click", ".AddRole", function () {
            sys.popFunc();
            form.on('submit(addRole)', function (data) {
                sys.roleAdd(data);//增加角色
            });
        });

        $(document).on("click",".RoleDelete",function(){
            console.log(this.name); //当前点击平台的id
            sys.roleDelete(this.name);
        });

        $(document).on("click",".RoleDetail",function(){
            console.log(this.name); //当前点击平台的id
            sys.roleDetail(this.name);
        });

        $(document).on("click",".RoleEdit",function(){
            console.log(this.name);  //当前点击管理员的id
            sys.roleEdit(this.name);
        });




    });

});
