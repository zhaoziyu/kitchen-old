$(document).ready(function () {

    // MetsiMenu
    $('#side-menu').metisMenu();

    var headHeight = 231;

    //固定菜单栏
    $(function () {
        $('.sidebar-collapse').slimScroll({
            height: $('.sidebar-collapse').parent().height() - headHeight,
            railOpacity: 0.9,
            alwaysVisible: false
        });
        $('.sidebar-collapse').css('top', headHeight);
    });

    // 菜单切换
    $('.navbar-minimalize').click(function () {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });

    // 侧边栏高度 // TODO 貌似没用，是否可移除
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper").height() - 61;
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
    }
    fix_height();

    $(window).bind("load resize click scroll", function () {
        if (!$("body").hasClass('body-small')) {
            fix_height();
        }
        var curHeight = $(".slimScrollDiv").parent().height() - headHeight;
        $(".slimScrollDiv").css('height', curHeight);
        $(".sidebar-collapse").css('height', curHeight);
    });

    //右侧的侧边栏滚动
    /*$(window).scroll(function () {
     if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
     $('#right-sidebar').addClass('sidebar-top');
     } else {
     $('#right-sidebar').removeClass('sidebar-top');
     }
     });

     $('.full-height-scroll').slimScroll({
     height: '100%'
     });*/

    $('#side-menu>li').click(function () {
        if ($('body').hasClass('mini-navbar')) {
            NavToggle();
        }
    });
    $('#side-menu>li li a').click(function () {
        if ($(window).width() < 769) {
            NavToggle();
        }
    });

    $('.mini-head-element').click(NavToggle);

    $('.nav-close').click(NavToggle);

    $('.layout-backdrop').click(function () {
        hideAside();
        NavToggle();
    });

    //ios浏览器兼容性处理
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        $('#content-main').css('overflow-y', 'auto');
    }

});

$(window).bind("load resize", function () {
    if ($(this).width() < 769) {
        $('body').addClass('mini-navbar');
        $('.navbar-static-side').fadeIn();

        $('.logo').css('display', 'none');
        $('.logo-mini').css('display', 'none');
    } else {
        if (!$('body').hasClass('mini-navbar')) {
            $('.logo').css('display', 'block');
            $('.logo-mini').css('display', 'none');
        } else if ($('body').hasClass('fixed-sidebar')) {
            $('.logo').css('display', 'none');
            $('.logo-mini').css('display', 'block');
        }
    }
});

function hideAside() {
    if ($("body").hasClass("layout-show-aside")) {
        $("body").removeClass("layout-show-aside");
        $(".layout-backdrop").css("z-index", -1);
    }
}

function NavToggle() {
    $('.navbar-minimalize').trigger('click');
}

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 100);
        if ($(window).width() < 769) {
            $('.logo').css('display', 'block');
            // 显示遮罩层
            $("body").toggleClass("layout-show-aside");
            $(".layout-backdrop").css("z-index", 1);
        } else {
            $('.logo').css('display', 'block');
            $('.logo-mini').css('display', 'none');
        }
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 300);
        if ($(window).width() < 769) {
            $('.logo').css('display', 'none');

            // 隐藏遮罩层
            hideAside();
        } else {
            $('.logo').css('display', 'none');
            $('.logo-mini').css('display', 'block');
        }
    } else {
        $('#side-menu').removeAttr('style');
    }
}