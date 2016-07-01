package com.diferdin.tests.obfuscatedmessagestore.config;

import com.diferdin.tests.obfuscatedmessagestore.dao.UserDao;
import com.diferdin.tests.obfuscatedmessagestore.service.SimpleUserService;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@ComponentScan("com.diferdin.tests.obfuscatedmessagestore")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public UserService getUserService() {
        return new SimpleUserService();
    }

    @Bean
    public UserDao getUserDao() {
        return new UserDao();
    }

//    @Bean
//    public View jsonTemplate() {
//        MappingJackson2JsonView view = new MappingJackson2JsonView();
//        view.setPrettyPrint(true);
//        return view;
//    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//        return new BeanNameViewResolver();
//    }
}
