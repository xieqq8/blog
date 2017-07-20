package com.xxx.blog.config;

import com.google.common.collect.Lists;
import com.xxx.blog.interfaces.manage.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author xiexx
 * @date 2016/11/16
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Value("${blog.manage.allow}")
    private String allowRemoteHosts;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/404").setViewName("404");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SecurityInterceptor interceptor=new SecurityInterceptor();
        interceptor.setAllowRemoteHosts(Lists.newArrayList(allowRemoteHosts.split(",")));
        registry.addInterceptor(interceptor).addPathPatterns("/admin/**");
    }



}
