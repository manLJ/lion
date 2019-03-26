package com.lion.core.web.annotation.resolver;

import com.alibaba.fastjson.JSONObject;
import com.lion.core.web.annotation.RequestParams;
import com.lion.core.web.model.RequestModel;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Iterator;

/**
 * Created by JACK on 2015/2/2.
 */
@Component
public class RequestParamsArgumentResolver implements HandlerMethodArgumentResolver {

    public RequestParamsArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParams.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Iterator<String> iterator = webRequest.getParameterNames();
        JSONObject params = new JSONObject();
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            params.put(paramName, webRequest.getParameter(paramName));
        }

        return new RequestModel(params);
    }
}
