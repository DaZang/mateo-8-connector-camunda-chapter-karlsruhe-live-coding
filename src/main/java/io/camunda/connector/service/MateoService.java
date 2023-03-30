package io.camunda.connector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;

import static io.camunda.zeebe.client.impl.Loggers.LOGGER;

@Service
public class MateoService {

    private RestTemplate restTemplate;

    public MateoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * start mateo script via mateo rest-api
     *
     * @param scriptName script path of the script to run (script has to be in the environment script directory of mateo)
     * @return response from mateo
     */
    public void executeScript(String scriptName) {
        LOGGER.info("Run the script: {}", scriptName);
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8123)
                .path("api/execute/script-files")
                .queryParam("filename", scriptName)
                .build()
                .toUri();
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = this.restTemplate
                .postForEntity(uri.toString(), entity, String.class);

        if (response.hasBody() && Objects.equals(response.getBody(), "Aborted"))
            throw new RuntimeException("Script execution aborted");

        if (response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
            throw new RuntimeException(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
