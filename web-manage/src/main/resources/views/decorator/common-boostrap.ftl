<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title><sitemesh:write property='title'/></title>

    <!-- jQuery -->
    <script src="/libs/jquery/jquery.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" type="text/css" href="/libs/font-awesome/css/font-awesome.css"/>
    <!-- Boostrap -->
    <link rel="stylesheet" href="/libs/bootstrap/css/bootstrap.min.css">
    <script src="/libs/bootstrap/js/bootstrap.min.js"></script>
    <!-- 滚动条美化 -->
    <script src="/libs/other/slimscroll/jquery.slimscroll.js"></script>

    <!--以下为嵌套页面引入的资源-->
    <sitemesh:write property='head'/>
</head>
<body id="bodyEL" name="bodyEL">

<sitemesh:write property='body' />

</body>

<script src="/frame/page_init.js"></script>

</html>