package com.restaurant.dinner.protal.controller.demo;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.pojo.vo.JsonVo;
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
    public JsonVo<DemoPerson> getDemoInfo(HttpServletRequest request, HttpSession httpSession) {
        JsonVo<DemoPerson> jsonVo = new JsonVo<>();

        DemoPerson demoPerson = demoService.getDemoPerson(1);

        jsonVo.setSuccess(true);
        jsonVo.setMsg("Hello World！");
        jsonVo.setData(demoPerson);

        return jsonVo;
    }

    @RequestMapping("/getRpcDemoInfo")
    @ResponseBody
    public JsonVo<DemoPerson> getRpcDemoInfo(HttpServletRequest request, HttpSession httpSession) {
        JsonVo<DemoPerson> jsonVo = new JsonVo<>();

        DemoService rpcDemoService = RpcClientProxy.createSync(DemoService.class);
        DemoPerson demoPerson = rpcDemoService.getDemoPerson(1);

        jsonVo.setSuccess(true);
        jsonVo.setMsg("Hello World！");
        jsonVo.setData(demoPerson);

        return jsonVo;
    }

    /**
     * 测试负载均衡
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping("/testLoadBalance")
    @ResponseBody
    public JsonVo<Object> testLoadBalance(HttpServletRequest request, HttpSession httpSession) {
        JsonVo<Object> jsonVo = new JsonVo<>();

        jsonVo.setSuccess(true);
        jsonVo.setMsg("IP：" + request.getRemoteAddr() + " Session：" + request.getRequestedSessionId());
        jsonVo.setData(null);

        return jsonVo;
    }
}
