package com.xxx.blog.config;

import com.google.common.eventbus.EventBus;
import com.xxx.blog.application.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiexx on 2017/1/6.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public EventBus eventBusForCache(EventHandler eventHandler){
        EventBus eventBus= new EventBus("cache-update-eventbus");
        eventBus.register(eventHandler);
        return eventBus;
    }
}
