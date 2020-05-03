package com.example.demo.command;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommandTest {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "tttt"
//            ,
//            commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "400"),//指定多久超时，单位毫秒。超时进fallback
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
//    }
    )
    public String getUrlByRestTemplate(String url) throws  Exception{
//        Thread.sleep(2000);
        String aa = restTemplate.getForObject(url, String.class);
        int a = 1/0;
//        throw new RuntimeException("getUserById command failed");
        return aa;
    }

    public String tttt(String url,Throwable e){
        System.out.println(e.getMessage());
        return "error";
    }
}
