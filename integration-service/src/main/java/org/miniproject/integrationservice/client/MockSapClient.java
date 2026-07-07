package org.miniproject.integrationservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class MockSapClient {
    private final RestClient restClient;

    private static final String SAP_URL =
            "http://localhost:8083/sap/orders";

    public String send(String xml){

        return restClient.post()
                .uri(SAP_URL)
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .body(xml)
                .retrieve()
                .body(String.class);

    }
}
