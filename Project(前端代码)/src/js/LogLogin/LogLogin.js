$(document).ready(function() {
    layui.use(['form','jquery','layer'], function () {
        var form = layui.form,
            $ = layui.jquery,
            layer = layui.layer;

        var Sys = function(){

        };
        Sys.prototype={
            loginError:function (msg) {
                layer.msg(msg, {
                    icon: 5,
                    time: 2000
                }, function (index) {
                    layer.close(index);
                });
            }, //获得本机ip
            loginSuccess:function() {
                layer.msg("登录成功", {
                    icon: 6,
                    time: 1000
                }, function (index) {
                    layer.close(index);
                });
            },
            loginFunc:function (data,ip) {
                $.ajax({
                    url: BASE_URL + '/user/login',
                    type: 'post',
                    data: {
                        uid: data.field.username,
                        upwd: data.field.password,
                        ip: ip,
                    },
                    success: function (result) {
                        console.log(result);
                        if (result === "1") {
                            sys.loginSuccess();
                            localStorage.setItem("user",data.field.username);
                            window.location.href = '../LogPlat/LogPlat.html';
                        } else if(result==="0"){
                            sys.loginSuccess();
                            localStorage.setItem("user",data.field.username);
                            window.location.href = '../LogIndex/LoginIndex.html';
                        }else {
                            sys.loginError("登录失败！");
                        }
                    },
                    error: function (result) {
                        console.log(result);
                        sys.loginError("登录失败！");
                    }
                });
            }

        };

        //全局方法
        var sys = new Sys();//类创建

        form.on('submit(login)', function (data) {
            var ip=returnCitySN.cip;

            sys.loginFunc(data,ip);
        });
    });

});
