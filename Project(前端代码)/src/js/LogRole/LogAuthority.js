layui.use(['jquery','layer'], function () {
    $(document).ready(function() {

        var Sys = function(){};

        Sys.prototype={

            msgError:function (msg){
                layer.msg(msg, {
                    icon: 5,
                    time: 2000
                }, function (index) {
                    layer.close(index);
                });
            },
            fatherMenu:function (res) {
                for( i=0;i<res.length;i++){
                    var tem ='<div style="height:40px;border-bottom:1px" onclick="childMenu('+res[i].id+')"> '+res[i].name+'</div>';
                    $("#fmenu").append(tem);
                    console.log("resid"+res[i].id);
                }
            }
        };
        //全局方法
        var sys = new Sys();//类创建

        $.ajax({
            url: BASE_URL+'/tree/getAllPid',
            type: 'post',
            success: function (res) {
                sys.fatherMenu(res);
            },
            error:function(res){
                sys.msgError("加载失败！");
            }
        });
    });

});
