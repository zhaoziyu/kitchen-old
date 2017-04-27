package com.restaurant.dinner.protal.controller.demo;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Demo
 *
 * @date 2016-12-23
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

        jsonObjectVo.setSuccess(true);
        jsonObjectVo.setMsg("Hello World！");
        jsonObjectVo.setData(demoPerson);

        return jsonObjectVo;
    }

    @RequestMapping("/getRpcDemoInfo")
    @ResponseBody
    public JsonObjectVo<DemoPerson> getRpcDemoInfo(HttpServletRequest request, HttpSession httpSession) {
        JsonObjectVo<DemoPerson> jsonObjectVo = new JsonObjectVo<>();

        DemoService rpcDemoService = RpcClientProxy.createSync(DemoService.class);
        DemoPerson demoPerson = rpcDemoService.getDemoPerson(1);

        jsonObjectVo.setSuccess(true);
        jsonObjectVo.setMsg("Hello World！");
        jsonObjectVo.setData(demoPerson);

        return jsonObjectVo;
    }

    /**
     * 测试负载均衡
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping("/testLoadBalance")
    @ResponseBody
    public JsonObjectVo<Object> testLoadBalance(HttpServletRequest request, HttpSession httpSession) {
        JsonObjectVo<Object> jsonObjectVo = new JsonObjectVo<>();

        jsonObjectVo.setSuccess(true);
        jsonObjectVo.setMsg("IP：" + request.getRemoteAddr() + " Session：" + request.getRequestedSessionId());
        jsonObjectVo.setData(null);

        return jsonObjectVo;
    }
}
