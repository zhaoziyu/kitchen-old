<!DOCTYPE html>
<html>

<head lang="zh-CN">
    <title>BEST Admin</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <meta name="keywords" content="网站关键词">
    <meta name="description" content="网站描述">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;url=/error_page/nonsupport-ie.html" />
    <![endif]-->

    <link rel="shortcut icon" href="favicon.ico">

    <link href="/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/frame/main/font-awesome/css/font-awesome.min.css" rel="stylesheet"><!-- 框架和iframe内页需要引用不同的font-awesome 否则会影响框架的图标 -->
    <link href="/libs/animate/animate.css" rel="stylesheet">
    <link href="/frame/main/main.css" rel="stylesheet">
    <link href="/frame/main/calculator/calculator.css" rel="stylesheet">
    <style type="text/css">
        body {
            font-family: "微软雅黑"
        }
    </style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div class="layout-backdrop"></div>
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">

        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>

        <div class="logo">
            <p>BEST Admin</p>
        </div>
        <div class="logo-mini">
            <p>BTA</p>
        </div>

        <div class="nav-header">
            <div class="dropdown profile-element">
                <span><img alt="image" class="img-circle" src="/images/frame/default_head.png" /></span>
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                    <span class="clear">
                        <span class="block m-t-xs"><strong class="font-bold">用户姓名</strong></span>
                        <span class="text-muted text-xs block">角色描述<b class="caret"></b></span>
                    </span>
                </a>
                <ul class="dropdown-menu animated fadeInUp m-t-xs">
                    <li><a href="form_avatar.html">修改头像</a></li><!-- TODO 弹出窗口进行修改 -->
                    <li><a href="profile.html">个人资料</a></li><!-- TODO 弹出窗口进行修改 -->
                    <li class="divider"></li>
                    <li><a href="/doLogout">安全退出</a></li>
                </ul>
            </div>
            <div class="mini-head-element">
                <img alt="image" class="mini-head-img" src="/images/frame/default_head.png" />
            </div>
        </div>

        <#include "decorator/main/menu.ftl">

    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="">
                        <div class="input-group" style="padding-left: 20px;">
                            <input type="text" class="form-control" placeholder="搜索 ……" style="width: 200px;">
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-flat" style="background-color: #2f4050;"><i style="color: #1ab394;" class="fa fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                </div>

                <#include "decorator/main/assist-function.ftl">

            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="welcome">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">选项卡<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabCloseAll"><a>关闭全部</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabShowActive"><a>定位</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="/index/welcome" frameborder="0" data-id="welcome" seamless></iframe>
        </div>

        <div class="footer">
            <div class="pull-right">
                Copyright © 2016 - 2017 &nbsp;&nbsp;<a href="#">赵梓彧</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
</div>

<!-- 全局js -->
<script src="/libs/jquery/jquery.min.js"></script>
<script src="/libs/bootstrap/js/bootstrap.min.js"></script>
<script src="/frame/main/jquery.metisMenu.js"></script>
<script src="/libs/other/slimscroll/jquery.slimscroll.js"></script>

<!-- 自定义js -->
<script src="/frame/main/main.js"></script>
<script src="/frame/main/contabs.js"></script>

<script src="/frame/main/calculator/calculator.js"></script>


<!-- 网页自动加载进度条插件 -->
<script src="/libs/other/pace/pace.min.js"></script>

<script type="text/javascript">
    // 重新登录
    function reLogin() {
        alert("重新登录窗口<br /> 发起Ajax重新登录");
    }
</script>


</body>

</html>