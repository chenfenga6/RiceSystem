$(document).ready(function() {

    layui.use(['tree', 'jquery', 'layer'], function () {
        // 操作对象
        var $ =layui.jquery
            ,layer = layui.layer;

        var Sys = function () {

        };

        Sys.prototype = {//类继承
            msgError: function (msg) {
                layer.msg(msg, {
                    icon: 5,
                    time: 2000
                }, function (index) {
                    layer.close(index);
                });
            },
            treeShow: function (data) {
                layui.tree.render({
                    elem: '#tree',
                    data: data,
                    showCheckbox: false,  //是否显示复选框
                    id: 'treeID',
                    isJump: true,//是否允许点击节点时弹出新窗口跳转
                });
            },
            fatherMenu:function (res) {
                for( i=0;i<res.length;i++){
                    var tem ='<div style="height:40px;border-bottom:1px" onclick="childMenu('+res[i].pid+')"> '+res[i].pname+'</div>';
                    $("#tree").append(tem);
                    console.log("resid"+res[i].pid);
                }
            },

        };

        var sys = new Sys();

        $.ajax({
            url: BASE_URL + '/tree/getPlatAuthorized',
            type: 'post',
            data: {
                uid: localStorage.getItem("user"),
            },
            success: function (res) {
                sys.fatherMenu(res);
            },
            error: function (res) {
                sys.msgError("加载失败！");
            }
        });
    });
});


