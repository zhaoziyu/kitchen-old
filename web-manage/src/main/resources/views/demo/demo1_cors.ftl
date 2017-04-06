<head>
    <title>Index</title>

    <!-- fileinput -->
    <link rel="stylesheet" type="text/css" href="/libs/bootstrap-plugin/bootstrap-fileinput/css/fileinput.css"/>
    <script type="text/javascript" src="/libs/bootstrap-plugin/bootstrap-fileinput/js/fileinput.js"></script>
    <script type="text/javascript" src="/libs/bootstrap-plugin/bootstrap-fileinput/js/fileinput-zh.js"></script>

</head>
<body>

<div style="padding-top: 100px;">
    <h1 align="center">Bonjour</h1>
    <button align="center" id="btnSend" class="btn-warning">Get Demo Info</button>
</div>

<div class="panel panel-success">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapsePictrue">
            <span class="panel-title">文件上传</span>
        </a>
    </div>

    <div id="collapsePictrue" class="panel-collapse collapse">
        <div class="panel-body">

            <div id="goodsImage">
                <div class="row">
                    <div style="width: 100%;padding-left: 20px;padding-right: 20px;">
                        <input type="file" name="file" id="goodsImageUpload" class="file-loading" multiple />
                    </div>
                </div>
                <br />
                <br />
            </div>

        </div>
    </div>
</div>

<script type="text/javascript">

    $(function () {
        $.getJSON('http://127.0.0.1:8080/demo/getDemoInfo', {}, function (result) {
            alert(result.data.name);
        });
        initFileInput("goodsImageUpload", "goods");
    });

    $("#btnSend").on("click", function () {
        $.getJSON('/demo/getDemoInfo', {}, function (result) {
            alert(result.data.name);
        });
    });

    $("#goodsImageUpload").on("fileuploaded", function (event, result, previewId, index) {
        var success = result.response.success;
        if (success) {
            alert(result.response.data.control);
            alert(result.response.data.imageUri);
        } else {
            alert(result.response.msg);
        }
    });
    function initFileInput(controlId, trench) {
        var fileControl = $('#' + controlId);

        var uploadUri = "http://127.0.0.1:8080/file/local/uploadSingle?control=" + controlId + "&trench=" + trench;

        fileControl.fileinput({
            language: 'zh',
            uploadUrl: uploadUri,
            allowedPreviewTypes: ['image'],
            allowedFileExtensions : ['jpg', 'png','gif'],
            browseClass: "btn btn-primary",
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            fileActionSettings: {
                showDrag: true
            }
        });
    }
</script>

</body>
