package com.vawo.foundation.stock.config;

import com.vawo.foundation.stock.utils.rest.BaseResultRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Autowired
    @Qualifier("httpMessageConverters")
    private HttpMessageConverters httpMessageConverters;

    @Bean(name = "restTemplate")
    RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30000);
        httpRequestFactory.setConnectTimeout(30000);
        httpRequestFactory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.setMessageConverters(httpMessageConverters.getConverters());
        return restTemplate;
    }

    @Bean(name = "baseResultRestClient")
    BaseResultRestClient baseResultRestClient() {
        BaseResultRestClient baseResultRestClient = new BaseResultRestClient(restTemplate());
        return baseResultRestClient;
    }
}
