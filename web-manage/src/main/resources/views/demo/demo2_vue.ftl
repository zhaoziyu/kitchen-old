<head>
    <title>Welcome</title>

</head>
<body>

<div style="padding-top: 100px;">
    <h1 align="center">Bonjour</h1>
    <h1 align="center">您好</h1>
    <h1 id="vue" align="center">{{ message }}</h1>
</div>

<div id="app-2" align="center">
  <span v-bind:title="message">
    鼠标停留在我这里，等待一会儿！
  </span>
</div>

<div id="app">
    <el-button @click="visible = true">按钮</el-button>
    <el-dialog v-model="visible" title="Hello world">
        <p>欢迎使用 Element</p>
    </el-dialog>
</div>

<script type="text/javascript">
    var app = new Vue({
        el: '#vue',
        data: {
            message: 'Hello Vue!'
        }
    })

    var app2 = new Vue({
        el: '#app-2',
        data: {
            message: 'You loaded this page on ' + new Date()
        }
    })

    new Vue({
        el: '#app',
        data: function(){
            return { visible: false }
        }
    })

    $("body").on("click", function () {
        app.message = "Hello Body!";
        app2.message = "点击Body的时间" + new Date();
    });
</script>

</body>