package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * 后置过滤器
 *记得类上加Component注解
 */
@Component
public class PostFiter extends ZuulFilter {

//    /**
//     * 过滤器类型，前置过滤器
//     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 过滤器是否生效
     * 返回true代表需要权限校验，false代表不需要用户校验即可访问
     */
    @Override
    public boolean shouldFilter() {

        //共享RequestContext，上下文对象
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//
//        System.out.println(request.getRequestURI());
//        //需要权限校验URL
//        if ("/client1/test".equalsIgnoreCase(request.getRequestURI())) {
//            return true;
//        } else  if ("/Client1Test/test".equalsIgnoreCase(request.getRequestURI())) {
//            return true;
//        }
//        else if ("/apigateway/order/api/v1/order/list".equalsIgnoreCase(request.getRequestURI())) {
//            return true;
//        } else if ("/apigateway/order/api/v1/order/find".equalsIgnoreCase(request.getRequestURI())) {
//            return true;
//        }
        return true;
    }

    /**
     * 业务逻辑
     * 只有上面返回true的时候，才会进入到该方法
     */
    @Override
    public Object run() throws ZuulException {

        //JWT
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();

        response.setHeader("haha", UUID.randomUUID().toString());
        return null;
    }
}