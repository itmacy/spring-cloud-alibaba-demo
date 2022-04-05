package com.itmacy.dev.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sentinel统一异常处理
 * author: itmacy
 * date: 2022/3/29
 */
@Component
public class SentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("sentinel exception" + e.getRule());

        ResponseData responseData = null;
        if (e instanceof FlowException){
            responseData = ResponseData.error(10,"接口限流了");
        }else if (e instanceof DegradeException){
            responseData = ResponseData.error(11, "服务降级了");
        }else if (e instanceof ParamFlowException) {
            responseData = ResponseData.error(12, "热点参数限流了");
        }else if (e instanceof SystemBlockException){
            responseData = ResponseData.error(13, "触发系统保护规则了");
        }else if (e instanceof AuthorityException){
            responseData = ResponseData.error(14, "授权规则不通过");
        }
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), responseData);
    }
}
