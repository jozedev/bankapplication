package com.jozedev.bankapplication.backend.account;

import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@SpringBootTest
@Testcontainers
public class IntegrationTest {

    private static final int ACCOUNT_PORT = 8000;
    private static final int CLIENT_PORT = 8001;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    @ClassRule
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("../docker-compose.yaml"))
                    .withExposedService("account", ACCOUNT_PORT)
                    .withExposedService("client", CLIENT_PORT);;


    @Test
    public void integrationTest() {
        String accoountUrl = environment.getServiceHost("account", ACCOUNT_PORT)
                + ":" +
                environment.getServicePort("account", ACCOUNT_PORT);

        String clientUrl = environment.getServiceHost("client", CLIENT_PORT)
                + ":" +
                environment.getServicePort("client", CLIENT_PORT);

        ResponseEntity<String> response = restTemplate.getForEntity(accoountUrl + "/api/accounts", String.class);
    }
}
