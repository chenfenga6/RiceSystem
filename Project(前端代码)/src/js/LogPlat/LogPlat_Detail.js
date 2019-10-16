layui.use(['element','tree','util','form'], function(){
    let tree = layui.tree;
    let util = layui.util;
    let form = layui.form;
    let treeData=[];
    let allFatherNode=[];
    console.log(localStorage.getItem("SystemDetail_Id")); //当前点击模块管理的平台id

    $(document).ready(function() {


        var Sys = function(){};//类声明

        Sys.prototype= {//类继承
            // 交换接口弹窗
            chgFunc: function () {
                //遍历所有的父节点
                find(treeData[0]);
                console.log(treeData[0]);
                console.log(allFatherNode);

                //清空下拉框内容
                $('#opType1').html('');
                $('#opType2').html('');
                //给两个节点的下拉框赋值
                for (let i in allFatherNode) {
                    let data = '<option>' + i + '</option>';
                    // console.log("111");
                    $('#opType1').append(data);
                    $('#opType2').append(data);
                }
                layer.open({
                    type: 1,
                    title: "节点交换",
                    area:["400px","300px"],
                    // skin: "sortNode",
                    content: $("#chgTable").html()
                });
                form.render('select');

                //添加节点
                form.on('submit(chgNode)', function (data) {
                    let sourceId = allFatherNode[data.field.node1];  //第一个节点id
                    let targetId = allFatherNode[data.field.node2];  //第二个节点id
                    console.log(sourceId);
                    console.log(targetId);
                    if (sourceId === 1 || targetId === 1) {
                        alert("不能修改根节点，请重新选择！");
                    } else if (sourceId === targetId) {
                        alert("请选择两个不同的节点进行更改！");
                    } else {
                        axios({
                            url: BASE_URL + '/tree/treeNodeSort',
                            method: 'POST',
                            params: {
                                pid: localStorage.getItem("SystemDetail_Id"),
                                sourceId: sourceId,
                                targetId: targetId,
                            },
                        }).then((res) => {
                            // alert(res.data);
                            // console.log(res.data);
                            window.location.reload();
                        });
                    }
                    // console.log(localStorage.getItem("SystemDetail_Id"));

                    // 提交给后端
                    return false;
                });
                function find(treeData) {
                    for (let k in treeData) {
                        if (k === 'title') {
                            allFatherNode[treeData.title] = treeData.id;
                        }
                        if (typeof treeData[k] === "object") {
                            find(treeData[k]);
                        }
                    }
                }
            },
            func1: function () {
                axios({
                    url: BASE_URL + '/tree/getTree',
                    method: 'POST',
                    params: {
                        pid: localStorage.getItem("SystemDetail_Id"),
                    },
                    contentType: "application/json",
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then((res) => {
                    console.log(res.data);
                    ResData = res.data;
                    $(".SystemName").text(ResData.name);    //面包屑导航赋值

                    if (ResData.data.length === 0) {
                        treeData = [{
                            title: ResData.name, //一级菜单
                            spread: true,
                            edit: ['del'],
                            children: []
                        }]
                    } else {
                        treeData = ResData.data;
                    }
                    tree.render({
                        id: ResData.id,
                        elem: '#module_left',//绑定元素
                        onlyIconControl: true,
                        edit: ['del'],
                        data: treeData,
                        click: function (obj) {
                            // console.log(obj);
                            let data = obj.data;  //获取当前点击的节点数据
                            // layer.msg('状态：' + obj.state + '<br>节点数据：' + JSON.stringify(data));
                            console.log(obj.data.id);   //点击节点的id

                            axios({
                                url: BASE_URL + '/tree/findTreeNode',
                                method: 'POST',
                                params: {
                                    pid: localStorage.getItem("SystemDetail_Id"),
                                    id: data.id,
                                },
                                contentType: "application/json",
                                headers: {
                                    'Content-Type': 'application/json'
                                }
                            }).then((res) => {
                                // console.log(res.data);
                                form.val("moduleMessageForm", {
                                    "fatherNode": res.data.name
                                    ,"nameEnglish": res.data.data[0].ename
                                    ,"nameChinese": res.data.data[0].cname
                                    ,"Notes": res.data.data[0].notes
                                    ,"Tag": res.data.data[0].tag
                                });
                            });


                        },
                        operate: function (obj) {
                            let type = obj.type; //得到操作类型：add、edit、del
                            let data = obj.data; //得到当前节点的数据
                            console.log(data);
                            let id = data.id; //得到节点索引
                            if (type === 'del') { //删除节点
                                //     给后端传id
                                console.log(data.id);
                                axios({
                                    url: BASE_URL + '/tree/delete',
                                    method: 'POST',
                                    params: {
                                        pid: localStorage.getItem("SystemDetail_Id"),
                                        id: data.id,
                                    },
                                    contentType: "application/json",
                                    headers: {
                                        'Content-Type': 'application/json'
                                    }
                                }).then((res) => {
                                    alert(res.data);
                                });

                            }
                        }
                    });
                });
            },
            func2: function () {
               document.getElementById("add_id").onclick=function(){
                   console.log("222");
                   $("#module_add").show();
                   $("#module_message").hide();

                   //遍历所有的父节点
                   find(treeData[0]);
                   console.log(treeData[0]);
                   console.log(allFatherNode);

                   //清空下拉框内容
                   $('#optionNode').html('');
                   for (let i in allFatherNode) {
                       let data = '<option>' + i + '</option>';
                       // console.log("111");
                       $('#optionNode').append(data);
                   }
                   form.render('select');

                   //添加节点
                   form.on('submit(addModule)', function (data) {
                       console.log(data);
                       console.log(data.field.fatherNode);  //添加的节点信息

                       let findId = allFatherNode[data.field.fatherNode];  //父节点的id
                       console.log(findId);
                       axios({
                           url: BASE_URL + '/tree/add',
                           method: 'POST',
                           params: {
                               pid: localStorage.getItem("SystemDetail_Id"),
                               cname: data.field.nameChinese,
                               ename: data.field.nameEnglish,
                               ppid: findId,
                               notes: data.field.notes,
                               tag: data.field.tag,
                           },
                           contentType: "application/json",
                           headers: {
                               'Content-Type': 'application/json'
                           }
                       }).then((res) => {
                           // alert(res.data);
                           window.location.reload();
                       });
                       //提交给后端
                       // return false;
                   });
                   function find(treeData) {
                       for (let k in treeData) {
                           if (k === 'title') {
                               allFatherNode[treeData.title] = treeData.id;
                           }
                           if (typeof treeData[k] === "object") {
                               find(treeData[k]);
                           }
                       }
                   }
               }
            },
            sort: function () {
                document.getElementById("sort_id").onclick=function () {
                    sys.chgFunc();
                }
            },
            msg: function () {
                document.getElementById("moduleMessage_id").onclick=function(){
                    $("#module_add").hide();
                    $("#module_message").show();
                }
            }
        };
        //全局方法
        var sys = new Sys();//类创建
        sys.func1();//类方法调用，相当于类的方法初始化。
        sys.func2();
        sys.sort(); //节点排序
        sys.msg();
    });
});




