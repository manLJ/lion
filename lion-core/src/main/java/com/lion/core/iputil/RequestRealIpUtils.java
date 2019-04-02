package com.lion.core.iputil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestRealIpUtils {
    private static final Logger logger = LoggerFactory.getLogger(RequestRealIpUtils.class);

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    public String getRequestRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        logger.info("x-forwarded-for = " + ip);

        if (StringUtils.isBlank(ip) || "unKnown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            logger.info("X-Real-IP = " + ip);
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.info("RemoteAddr = " + ip);
        }

        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (StringUtils.isNotBlank(ip) && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if (StringUtils.isBlank(ip)) {
            logger.error("从request取ip失败。");
        }
        return ip;
    }

}
