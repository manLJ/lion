package com.lion.config.web;

import com.lion.core.exception.ApplicationException;
import com.lion.systemmgr.auth.domain.service.IdentityAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebRequestInterceptor implements HandlerInterceptor {

    @Resource
    private IdentityAuthentication identityAuthentication;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRequestInterceptor.class);

    /*
     * 进入controller层之前拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws IOException {
        String token = request.getHeader("X-Auth-Token");
        //用户身份合法性校验
        try {
            identityAuthentication.validate(token, request);
        } catch (ApplicationException e) {
            LOGGER.error(String.format("请求拦截：%s", e.getMessage()));
            //这句话的意思，是让浏览器用utf8来解析返回的数据
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
            response.setCharacterEncoding("UTF-8");

            response.sendError(401, e.getMessage());
            return false;
        }
        identityAuthentication.setEffectiveTimes(request);
        return true;
    }

}
