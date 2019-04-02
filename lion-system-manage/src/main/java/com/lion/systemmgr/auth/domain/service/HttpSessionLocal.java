package com.lion.systemmgr.auth.domain.service;

import com.lion.core.identity.RequestIdentity;
import com.lion.systemmgr.organization.domain.model.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class HttpSessionLocal {

    public static User getCurrentUser() {
        RequestIdentity identity = (RequestIdentity) HttpSessionLocal.getAttribute(getRequest().getHeader("X-Auth-Token"));
        if (identity == null || !(identity.getUser() instanceof User)) return null;
        return (User) identity.getUser();
    }

    public static void setAttribute(String key, Object obj) {
        getRequest().getSession().setAttribute(key, obj);
    }

    public static Object getAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    public static void removeAttribute(String key) {
        getRequest().getSession().removeAttribute(key);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
