package com.salesmanager.shop.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
public class AddRandomDelayAspectConfiguration {

    @Bean
    public RandomDelayAspect randomDelayAspect () {
        return new RandomDelayAspect();
    }
}
