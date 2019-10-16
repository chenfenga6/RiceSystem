layui.use(['layer','form'], function() {
    var layer = layui.layer,
        form = layui.form;

    $(document).ready(function() {

        var Sys = function () {
        };

        Sys.prototype = {
            popFunc: function () {//弹窗
                layer.open({
                    type:1,
                    title:"添加平台系统",
                    area:["40%"],
                    skin:"addSystem",
                    content:$("#sysTable").html()
                });
            },
            systemAdd: function (data) {
                console.log(data.field);
                //提交给后端
                axios({
                    url:BASE_URL+'/platform/add',
                    method:'POST',
                    params:{
                        pname:data.field.name,
                        purl:data.field.url,
                        pcode:data.field.code,
                        plog:"",
                    },
                    contentType:"application/json",
                    headers:{
                        'Content-Type':'application/json'
                    }
                }).then((res)=> {
                    location.reload();
                });

            },
            systemDelete: function (id) {
                axios({
                    url:BASE_URL+'/platform/delete',
                    method:'POST',
                    params:{
                        pid:id,
                    },
                    contentType:"application/json",
                    headers:{
                        'Content-Type':'application/json'
                    }
                }).then((res)=> {
                    console.log(res.data);
                    layui.use(['layer'], function () {
                        let layer = layui.layer;
                        layer.confirm('删除不可恢复，是否删除？', {icon: 3, title: '提示'}, function (index) {

                            //将要删除的平台id发送给后端。

                            layer.close(index);
                            location.reload();
                        });
                    });
                })



            },
            systemDetail: function (id) {
                localStorage.removeItem("SystemDetail_Id");
                localStorage.setItem("SystemDetail_Id", id);
                window.location.href="../LogPlat/LogPlat_Detail.html";
            },
            systemEdit: function (id) {
                axios({
                    url:BASE_URL+'/platform/find',
                    method:'POST',
                    params:{
                        pid:id,
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
                            title: "添加平台系统",
                            area: ["40%"],
                            skin: "addSystem",
                            content: $("#sysTable").html()
                        });

                        //
                        //
                        //传平台的id给后端  从后端取当前平台的基本信息，
                        //
                        //
                        let data={
                            name:res.data.pname,
                            code:res.data.pcode,
                            url:res.data.purl,
                        };
                        form.val("addSystem", {
                            "name": data.name,
                            "code": data.code,
                            "url": data.url
                        });

                        form.on('submit(addSystem)', function (data) {
                            console.log(data.field);

                            axios({
                                url:BASE_URL+'/platform/change',
                                method:'POST',
                                params:{
                                    pid:res.data.pid,
                                    pname:data.field.name,
                                    pcode:data.field.code,
                                    purl:data.field.url,
                                    plog:res.data.plog,   //图标待修改
                                },
                                contentType:"application/json",
                                headers:{
                                    'Content-Type':'application/json'
                                }
                            }).then((res)=> {
                                // alert(res.data);
                                location.reload();
                            });
                            //提交给后端
                            return false;
                        });
                    });
                });


            },
            systemManage: function () {
                axios({
                    url: BASE_URL + '/platform/findAll',
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
                            '                    <td>' + i + '</td>\n' +
                            '                    <td>' + tableData[i].pname + '</td>\n' +
                            '                    <td>' + tableData[i].pcode + '</td>\n' +
                            '                    <td>' + tableData[i].purl + '</td>\n' +
                            '                    <td>' + tableData[i].plog + '</td>\n' +
                            '                    <td>\n' +
                            '                        <button class="layui-btn layui-btn-xs SystemEdit" name="' + tableData[i].pid + '">编辑</button>\n' +
                            '                        <button class="layui-btn  layui-btn-danger layui-btn-xs SystemDelete" name="' + tableData[i].pid + '" >删除</button>\n' +
                            '                        <button class="layui-btn  layui-btn-warm layui-btn-xs SystemDetail" name="' + tableData[i].pid + '">模块管理</button>\n' +
                            '                    </td>\n' +
                            '                </tr>';
                        $('#tableBody').append(popContent);
                    }
                });
            }
        };

        var sys = new Sys();
        sys.systemManage();

        $(document).on("click", ".AddSystem", function () {
            sys.popFunc();
            form.on('submit(addSystem)', function(data){
                sys.systemAdd(data);//增加角色
                return false;
            });
        });

        $(document).on("click",".SystemDelete",function(){
            console.log(this.name);  //当前点击平台的id
            sys.systemDelete(this.name);
        });

        $(document).on("click",".SystemDetail",function(){
            console.log(this.name); //当前点击平台的id
            sys.systemDetail(this.name);
        });

        $(document).on("click",".SystemEdit",function(){
            console.log(this.name);  //当前点击平台的id
            sys.systemEdit(this.name);
        });
    });

});
