<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title><sitemesh:write property='title'/></title>

    <!-- Vue.js -->
    <script src="/libs/vue/vue.js"></script>
    <!-- jQuery -->
    <script src="/libs/jquery/jquery.min.js"></script>
    <!-- Element - 基于Vue2的UI -->
    <link rel="stylesheet" href="/libs/vue/element/theme-default/index.css">
    <script src="/libs/vue/element/index.js"></script>
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