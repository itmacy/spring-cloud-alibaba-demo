package com.itmacy.dev.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/** 自定义openfeign拦截器
 * author: itmacy
 * date: 2022/3/28
 */
public class OpenFeignCustomInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 请求远程服务前设置请求头
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null){
            HttpServletRequest request = attributes.getRequest();
            String authorization = request.getHeader("Authorization");
            // 添加请求头
            requestTemplate.header("Authorization", authorization);
        }
    }
}
