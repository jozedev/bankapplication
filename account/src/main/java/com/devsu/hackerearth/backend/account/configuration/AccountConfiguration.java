package com.devsu.hackerearth.backend.account.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableConfigurationProperties(ServicesProperties.class)
@PropertySource("classpath:endpoints.properties")
public class AccountConfiguration {

    private ServicesProperties servicesProperties;

    @Bean
    public RestTemplate clientRestTemplate(ServicesProperties servicesProperties) {
        return new RestTemplate();
    }
}
