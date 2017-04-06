$(document).ready(function () {
    if(window != top) {// 只有作为框架的内页时才需要设置
        // 点击内页如同点击了框架的其它位置（可以让框架中弹出的内容复原）
        $("body").on("click", function () {
            // 调用iframe父页面中body的click事件
            $(window.parent.document).find("body").click();
        });
    }
    // 滚动条样式
    $("body").slimScroll({
        height: $(window).height(),
        railOpacity: 0.9,
        alwaysVisible: false
    });
    $(window).bind("load resize click scroll", function () {
        var curHeight = $(window).height();
        $(".slimScrollDiv").css('height', curHeight);
        $("body").css('height', curHeight);
    });
});