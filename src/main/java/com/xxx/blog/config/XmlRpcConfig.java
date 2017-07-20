package com.xxx.blog.config;

import com.xxx.blog.interfaces.manage.metaweblog.MetaweblogServerlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiexx
 * @date 2017/3/31
 */
@Configuration
public class XmlRpcConfig {
    @Bean
    public ServletRegistrationBean servletRegistrationBean(MetaweblogServerlet metaweblogServerlet){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean();
        servletRegistrationBean.setServlet(metaweblogServerlet);
        servletRegistrationBean.addUrlMappings("/metaweblog/api");
        servletRegistrationBean.setEnabled(true);
        return servletRegistrationBean;
    }
}
