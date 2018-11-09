package com.deliver.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by pdl on 2018/10/9.
 */
@Configuration
public class DefaultView  extends WebMvcConfigurerAdapter {
    //private static Logger logger = Logger.getLogger();

    @Override
    //@GetMapping("/templates")
    public void addViewControllers(ViewControllerRegistry registry) {
        String homePage = "";
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.cfg"));
            homePage = props.getProperty("home.page");
        } catch (IOException e) {
            //logger.error(e.getMessage());
            homePage = "/loginPage.html";
        }


        registry.addViewController("/").setViewName("redirect:" + "/loginPage.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);

    }



}