package com.example.demo.filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfiguration {
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParameter = new HashMap<>();
        initParameter.put("allow","127.0.0.1");
        initParameter.put("deny","192.168.1.73");
        initParameter.put("loginUsername","admin");
        initParameter.put("loginPassword","123456");
        initParameter.put("resetEnable","false");
        servletRegistrationBean.setInitParameters(initParameter);
//        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
//        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
//        servletRegistrationBean.addInitParameter("loginUsername","admin");
//        servletRegistrationBean.addInitParameter("loginPassword","123456");
//        servletRegistrationBean.addInitParameter("resetEnable","false");
         return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}