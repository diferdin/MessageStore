package com.diferdin.tests.obfuscatedmessagestore.config;

import com.diferdin.tests.obfuscatedmessagestore.dao.MessageDao;
import com.diferdin.tests.obfuscatedmessagestore.dao.UserDao;
import com.diferdin.tests.obfuscatedmessagestore.service.MessageService;
import com.diferdin.tests.obfuscatedmessagestore.service.SimpleMessageService;
import com.diferdin.tests.obfuscatedmessagestore.service.SimpleUserService;
import com.diferdin.tests.obfuscatedmessagestore.service.UserService;
import com.diferdin.tests.obfuscatedmessagestore.service.obfuscation.Obfuscator;
import com.diferdin.tests.obfuscatedmessagestore.service.obfuscation.Rot20Obfuscator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

    @Bean
    public MessageService getMessageService() {
        return new SimpleMessageService();
    }

    @Bean
    public MessageDao getMessageDao() {
        return new MessageDao();
    }

    @Bean
    public Obfuscator getObfuscator() {
        return new Rot20Obfuscator();
    }

}
