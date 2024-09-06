package com.jozedev.bankapplication.backend.account.service;

import com.jozedev.bankapplication.backend.account.configuration.ServicesProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceClientImpl implements ServiceClient {

    private final RestTemplate restTemplate;
    private final ServicesProperties servicesProperties;

    public ServiceClientImpl(RestTemplate restTemplate, ServicesProperties servicesProperties) {
        this.restTemplate = restTemplate;
        this.servicesProperties = servicesProperties;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String serviceKey, Object requestBody, Class<T> responseType, Object... uriVariables) {
        String baseUri = servicesProperties.getClient().getBaseUri();
        String serviceUri = servicesProperties.getClient().getServices().get(serviceKey);
        String uri = baseUri + serviceUri;

        return restTemplate.postForEntity(uri, requestBody, responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String serviceKey, Class<T> responseType, Object... uriVariables) {
        String baseUri = servicesProperties.getClient().getBaseUri();
        String serviceUri = servicesProperties.getClient().getServices().get(serviceKey);
        String uri = baseUri + serviceUri;

        return restTemplate.getForEntity(uri, responseType, uriVariables);
    }
}
