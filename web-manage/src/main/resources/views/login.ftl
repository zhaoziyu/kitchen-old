<!DOCTYPE html>
<html>
<head lang="zh-cn">

    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>登录</title>

    <script type="text/javascript">
        if(window != top){
            // 策略1：调用外部框架的登录窗口
            //top.reLogin();
            // 策略2：从外部框架跳转到登录页面
            top.location.href = location.href;
        }
    </script>

    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="/libs/layui/css/layui.css" />
    <link rel="stylesheet" type="text/css" href="/frame/login/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/libs/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/frame/login/form-elements.css" />
    <link rel="stylesheet" type="text/css" href="/frame/login/style.css" />
    <style type="text/css">
        body {
            font-family: "微软雅黑"
        }
    </style>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/frame/login/html5shiv.js"></script>
    <script src="/frame/login/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>${projectName}</strong></h1>

                    <div class="description">
                        <p>
                            ${loginSubhead}
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <!-- TODO 多种登录方式：手机验证码登录、二维码扫描登录等 -->
                            <h3>密码登录</h3>

                            <p>请输入注册的手机号/用户名和登录密码</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" method="post" class="login-form" id="loginForm">
                            <div class="form-group">
                                <label class="sr-only" for="formCode"></label>
                                <input type="text" id="formCode" name="formCode" placeholder="手机号或用户名..." class="form-username form-control">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="formPassword"></label>
                                <input type="password" id="formPassword" name="formPassword" placeholder="登录密码..." class="form-password form-control">
                            </div>
                            <label><input type="checkbox" id="ck_rmbUser" />&nbsp;&nbsp;记住我</label>
                            <button type="button" class="btn" id="btnLogin">立即登录</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Javascript -->
<script src="/frame/login/jquery-1.11.1.min.js"></script>
<script src="/frame/login/bootstrap.min.js"></script>
<script src="/frame/login/jquery.backstretch.min.js"></script>
<script src="/frame/login/scripts.js"></script>
<script src="/libs/layui/layui.js"></script>
<script src="/libs/jquery-plugin/jquery.cookie.js"></script>

<script type="text/javascript">
    var layer = null;
    $(function () {
        layui.use(['layer'], function(){
            layer = layui.layer;
        });

        if ($.cookie("KitchenUser") == "true") {
            $("#ck_rmbUser").attr("checked", true);
            $("#formCode").val($.cookie("username"));
            $("#formPassword").val($.cookie("password"));
        }
    });

    //记住用户名密码
    function saveCookie() {
        if ($("#ck_rmbUser").prop("checked")) {
            var str_username = $("#formCode").val();
            var str_password = $("#formPassword").val();
            $.cookie("KitchenUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
            $.cookie("username", str_username, { expires: 7 });
            $.cookie("password", str_password, { expires: 7 });
        } else {
            $.cookie("KitchenUser", "false", { expire: -1 });
            $.cookie("username", "", { expires: -1 });
            $.cookie("password", "", { expires: -1 });
        }
    }

    function doLogin() {
        var loginCode = $("#formCode").val();
        if (!loginCode) {
            showTips('必填', '#formCode', 2);
            return;
        }
        var loginPassword = $("#formPassword").val();
        if (!loginPassword) {
            showTips('必填', '#formPassword', 2);
            return;
        }
        $('#btnLogin').attr('disabled',"true");
        saveCookie();
        layer.load();
        $.getJSON("/doLogin", {code: loginCode, password: loginPassword}, function(result) {
            layer.closeAll('loading');
            if (result.success == true) {
                // 跳转至主页
                window.location.href = "/";
            } else {
                $('#btnLogin').removeAttr("disabled");
                showTips(result.msg, '#btnLogin', 3);
                return;
            }
        });
    }

    function showTips(msg, boundId, place) {
        layer.tips(msg, boundId, {
            tips: [place, '#3595CC'],
            time: 2000
        });
    }

    function enterKeyLogin(e) {
        var e = e || event;
        if ((e['keyCode'] == 13) || (e['which'] == 13)) {
            $("#btnLogin").trigger('click');
        }
    }

    $("#btnLogin").click(doLogin);

    $("#formCode").click(function() {
        $(this).select();
    });
    $("#formPassword").click(function() {
        $(this).select();
    });

    document.onkeypress = enterKeyLogin; //回车登录

    // TODO 验证输入的登录信息格式
</script>

<!--[if lt IE 10]>
<script src="/frame/login/placeholder.js"></script>
<![endif]-->

</body>

</html>