package org.miniproject.integrationservice.adapter;

import org.miniproject.integrationservice.client.MockSapClient;
import org.miniproject.integrationservice.dto.SapResponse;
import org.miniproject.integrationservice.util.XmlConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SapAdapter {

    private final MockSapClient client;
    private final XmlConverter xmlConverter;

    public SapResponse send(String xml){

        log.info("========== SAP REQUEST ==========");
        log.info("\n{}", xml);

        String responseXml = client.send(xml);

        log.info("========== SAP RESPONSE ==========");
        log.info("\n{}", responseXml);

        return xmlConverter.fromXml(
                responseXml,
                SapResponse.class
        );

    }
}
