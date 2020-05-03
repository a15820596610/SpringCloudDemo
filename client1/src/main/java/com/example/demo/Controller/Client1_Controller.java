package com.example.demo.Controller;


import com.example.demo.Service.TestService;
import com.example.demo.command.CommandTest;
import com.netflix.discovery.util.StringUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;

@Controller
public class Client1_Controller {
    @Autowired
    private CommandTest commandTest;

    @Autowired
    private TestService testService;

//    @Autowired
//    private DiscoveryClient discoveryClient;// Eureka客户端，可以获取到服务实例信息

    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public String getData(HttpServletResponse response) throws Exception {
        Thread.sleep(2000);
        return "延迟了2秒，我是Client1,哈哈";
    }

    @ResponseBody
    @RequestMapping(value = "/getData1", method = RequestMethod.GET)
    public String getData1(HttpServletResponse response) throws Exception {
//        Thread.sleep(2000);
        return "我是Client1,哈哈";
    }

    @ResponseBody
    @RequestMapping(value = "/getClient2_data", method = RequestMethod.GET)
    public String getUrl(HttpServletResponse response) throws Exception {
        String baseUrl = "http://client2/getData";
        String result = commandTest.getUrlByRestTemplate(baseUrl);
        System.out.println("result=" + result);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request) throws Exception {
        HashMap<String,String> hashMap = buildParameterMap(request);
        String test = hashMap.get("accessToken");
        if (test != null) {
//            System.out.println("收到前置拦截器的值，test=" + test);
        }

        String result = testService.testTransactional();
        System.out.println("result=" + result);
        return result;
//        throw new RuntimeException("getUserById command failed");
    }

    public HashMap buildParameterMap(HttpServletRequest request) {
        HashMap params = new HashMap();
        Enumeration e = request.getParameterNames();
        String key = "";
        String value = "";
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            value = (String) request.getParameter(key);
            params.put(key, value);
        }
        return params;
    }

}
