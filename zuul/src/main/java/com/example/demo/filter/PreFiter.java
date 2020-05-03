package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 登录过滤器
 *记得类上加Component注解
 */
@Component
public class PreFiter extends ZuulFilter {

//    /**
//     * 过滤器类型，前置过滤器
//     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        System.out.println(request.getRequestURI());
        //需要权限校验URL
        if ("/client1/test".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        } else  if ("/Client1Test/test".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
//        else if ("/apigateway/order/api/v1/order/list".equalsIgnoreCase(request.getRequestURI())) {
//            return true;
//        } else if ("/apigateway/order/api/v1/order/find".equalsIgnoreCase(request.getRequestURI())) {
//            return true;
//        }
        return false;
    }

    /**
     * 业务逻辑
     * 只有上面返回true的时候，才会进入到该方法
     */
    @Override
    public Object run() throws ZuulException {

        //JWT
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 每次在参数中固定传递 accessToken
        Map<String, List<String>> requestQueryParams = requestContext.getRequestQueryParams();
        if (requestQueryParams==null) requestQueryParams=new HashMap<>();

        ArrayList<String> paramsList = new ArrayList<>();
        String test = "xxx";
        paramsList.add(test);
        requestQueryParams.put("accessToken", paramsList);
        requestContext.setRequestQueryParams(requestQueryParams);






        //token对象,有可能在请求头传递过来，也有可能是通过参数传过来，实际开发一般都是请求头方式
        String token = request.getHeader("token");

        if (StringUtils.isBlank((token))) {
            token = request.getParameter("token");
        }
//        System.out.println("页面传来的token值为：" + token);
        //登录校验逻辑  如果token为null，则直接返回客户端，而不进行下一步接口调用
        if (StringUtils.isBlank(token)) {
            // 过滤该请求，不对其进行路由
            requestContext.setSendZuulResponse(false);
            //返回错误代码
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponseBody("没有Token值");
        }
        return null;
    }
}