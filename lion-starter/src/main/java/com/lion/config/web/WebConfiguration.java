package com.lion.config.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lion.core.web.annotation.resolver.RequestParamsArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By JACK  2018/1/30
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestParamsArgumentResolver requestParamsArgumentResolver;


    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(requestParamsArgumentResolver);
    }

    @Bean
    public ServletRegistrationBean apiDispatcherRegistration() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        DispatcherServlet apiDispatcher = new DispatcherServlet(applicationContext);
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(apiDispatcher);
        servletRegistrationBean.addUrlMappings("/api/*");
        servletRegistrationBean.setName("apiDispatcher");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getJsonConverter()); //增加支持json格式的转换converter
        converters.add(new MappingJackson2HttpMessageConverter()); //增加支持application/*+json格式的转换converter
        converters.add(new ResourceHttpMessageConverter()); //增加*/*格式转换的converter
    }

    @Bean
    public HttpMessageConverter getJsonConverter() {
        //1、先定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2、添加fastjson的配置信息，比如是否要格式化返回的json数据；
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig config = new SerializeConfig();
        fastJsonConfig.setSerializeConfig(config);

        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                //  输出key是包含双引号
                SerializerFeature.QuoteFieldNames,
                //  是否输出为null的字段,若为null 则显示该字段
                SerializerFeature.WriteMapNullValue,
                //  数值字段如果为null，则输出为0
                SerializerFeature.WriteNullNumberAsZero,
                //   List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                //  字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteNullStringAsEmpty,
                //  Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                //  Date的日期转换器
                SerializerFeature.WriteDateUseDateFormat,
                //  循环引用
                SerializerFeature.DisableCircularReferenceDetect,
        };
        fastJsonConfig.setSerializerFeatures(serializerFeatures);

        //附加：处理中文乱码
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        //3、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return fastConverter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        //设置文件大小上限为100 M，默认不设上限
        multipartResolver.setMaxInMemorySize(100 * 1024 * 1024);
        multipartResolver.setMaxUploadSize(100 * 1024 * 1024);
        return multipartResolver;
    }

}
