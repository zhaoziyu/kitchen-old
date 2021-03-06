package com.restaurant.dinner.admin.controller.demo;

import com.restaurant.dinner.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoService;
import com.restaurant.dinner.market.common.time.KitTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Demo
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/getDemoInfo")
    @ResponseBody
    public JsonObjectVo<DemoPerson> getDemoInfo(HttpServletRequest request, HttpSession httpSession) {
        JsonObjectVo<DemoPerson> jsonObjectVo = new JsonObjectVo<>();

        DemoPerson demoPerson = demoService.getDemoPerson(1);

        jsonObjectVo.setSuccess(false);
        jsonObjectVo.setMsg("Hello World！");
        jsonObjectVo.setData(demoPerson);

        return jsonObjectVo;
    }

    @RequestMapping("/testConcurrence")
    @ResponseBody
    public JsonObjectVo<String> testConcurrence(HttpServletRequest request, String user_id) {
        System.out.println(user_id + "调用testConcurrence请求");
        synchronized (user_id) {
            System.out.println(user_id + "开始执行testConcurrence请求");
            String startTime = KitTimeFormatter.formatNowToCommonDateTimeMs();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String endTime = KitTimeFormatter.formatNowToCommonDateTimeMs();
            JsonObjectVo<String> jsonObjectVo = new JsonObjectVo<>();
            jsonObjectVo.setSuccess(true);
            jsonObjectVo.setMsg("start:" + startTime);
            jsonObjectVo.setData("end:" + endTime);
            return jsonObjectVo;
        }
    }

    @RequestMapping(value = "/demo1")
    public String demo1(ModelMap map) {
        return "demo/demo1_cors";
    }

    @RequestMapping(value = "/demo2")
    public String demo2(ModelMap map) {
        return "demo/demo2_vue";
    }

}
