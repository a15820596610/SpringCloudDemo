package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;


@Controller
public class Client_22_Controller {
    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private DiscoveryClient discoveryClient;// Eureka客户端，可以获取到服务实例信息

    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public String getExcel(HttpServletResponse response) {
        return "我是Client22,哈哈";
    }

    @ResponseBody
    @RequestMapping(value = "/getClient2_data", method = RequestMethod.GET)
    public String getClient2_data(HttpServletResponse response) {
        String baseUrl = "http://127.0.0.1:8703/getData";
        String sss = this.restTemplate.getForObject(baseUrl, String.class);
        return sss;
    }
}