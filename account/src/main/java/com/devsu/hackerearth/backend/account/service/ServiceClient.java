package com.devsu.hackerearth.backend.account.service;

import org.springframework.http.ResponseEntity;

public interface ServiceClient {

    <T> ResponseEntity<T> postForEntity(String serviceKey, Object requestBody, Class<T> responseType, Object... uriVariables);

    <T> ResponseEntity<T> getForEntity(String serviceKey, Class<T> responseType, Object... uriVariables);
}
