package com.zslin.bus.common.controller;

import com.zslin.basic.tools.Base64Utils;
import com.zslin.bus.tools.JsonParamTools;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * Created by zsl on 2018/7/3.
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private BeanFactory factory;

    /**
     *
     *  - action - 具体的处理业务
     *  - params - 对应的json参数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "baseRequest")
    public JsonResult baseRequest(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("auth-token"); //身份认证token
        String apiCode = request.getHeader("api-code"); //接口访问编码
        if(token == null || "".equals(token) || apiCode==null || "".equals(apiCode)) {
            return JsonResult.getInstance().fail("auth-token或api-code为空");
        }
        try {
            String serviceName = apiCode.split("\\.")[0];
            String actionName = apiCode.split("\\.")[1];
            Object obj = factory.getBean(serviceName);
            Method method ;
            boolean hasParams = false;
            String params = request.getParameter("params");
            if(params==null || "".equals(params.trim())) {
                method = obj.getClass().getMethod(actionName);
            } else {
                params = Base64Utils.getFromBase64(params);
                params = URLDecoder.decode(params, "utf-8");
//                System.out.println("============="+params);

                params = JsonParamTools.rebuildParams(params, request);

                method = obj.getClass().getMethod(actionName, params.getClass());
                hasParams = true;
            }
            JsonResult result;
            if(hasParams) {
                result = (JsonResult) method.invoke(obj, params);
            } else {
                result = (JsonResult) method.invoke(obj);
            }
//            System.out.println("result: "+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.getInstance().fail("数据请求失败："+e.getMessage());
        }
    }
}
