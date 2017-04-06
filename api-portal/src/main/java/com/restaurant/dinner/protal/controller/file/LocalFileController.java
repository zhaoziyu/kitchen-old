package com.restaurant.dinner.protal.controller.file;

import com.restaurant.dinner.api.pojo.vo.JsonVo;
import com.restaurant.dinner.market.common.file.KitFileHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传接口
 *
 * @date 2016-12-30
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping(value = "/file/local")
public class LocalFileController {

    @RequestMapping(value = "/uploadSingle", method = RequestMethod.POST)
    @ResponseBody
    public JsonVo<Map<String, String>> uploadSingle(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        String folder = request.getParameter("folder");     //要上传到的子文件夹，若不传，则上传至upload根目录
        String control = request.getParameter("control");   //上传控件的id，将原样返回给前端

        boolean success = true;
        String msg = "";
        String uploadedUri = "";
        if (file != null) {
            uploadedUri = KitFileHelper.uploadToLocal(file, folder);
            success = true;
            msg = "上传成功";
        } else {
            success = false;
            msg = "未找到要上传的文件";
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("control", control);
        resultMap.put("imageUri", uploadedUri);

        JsonVo<Map<String, String>> result = new JsonVo<>();
        result.setSuccess(success);
        result.setMsg(msg);
        result.setData(resultMap);

        return result;
    }

    @RequestMapping(value = "/uploadMulti", method = RequestMethod.POST)
    @ResponseBody
    public JsonVo<Map<String, Object>> uploadMulti(@RequestParam(value = "files", required = false) MultipartFile[] files, HttpServletRequest request) {
        String folder = request.getParameter("folder");     //要上传到的子文件夹，若不传，则上传至upload根目录
        String control = request.getParameter("control");  //上传的控件（id或name），将原样返回给前端

        boolean success = true;
        String msg = "";
        List<String> uploadedUri = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                try {
                    String uri = KitFileHelper.uploadToLocal(file, folder);
                    uploadedUri.add(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (files.length == uploadedUri.size()) {
                success = true;
                msg = "上传成功";
            } else {
                success = false;
                msg = "上传成功：" + uploadedUri.size() + "个，上传失败：" + (files.length - uploadedUri.size()) + "个";
            }
        } else {
            success = false;
            msg = "未找到要上传的文件";
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("control", control);
        resultMap.put("imageUriList", uploadedUri);

        JsonVo<Map<String, Object>> result = new JsonVo<>();
        result.setSuccess(success);
        result.setMsg(msg);
        result.setData(resultMap);

        return result;
    }
}
