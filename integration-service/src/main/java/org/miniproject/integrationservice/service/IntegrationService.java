package org.miniproject.integrationservice.service;
import org.miniproject.integrationservice.dto.SapOrderRequest;
import org.miniproject.integrationservice.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.integrationservice.mapper.OrderMapper;
import org.miniproject.integrationservice.util.XmlConverter;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final OrderMapper mapper;
    private final XmlConverter xmlConverter;

    public void process(OrderCreatedEvent event) {

        log.info("====================================");
        log.info("Order Event Received");
        log.info("orderId = {}", event.getOrderId());
        log.info("customerId = {}", event.getCustomerId());
        log.info("productId = {}", event.getProductId());
        log.info("quantity = {}", event.getQuantity());
        log.info("====================================");

        // 검증
        validate(event);

        // Mapping : event -> saprequest
        SapOrderRequest request = mapper.toSapRequest(event);

        // xml 생성
        String xml = xmlConverter.toXml(request);

        log.info("SAP XML Request");
        log.info(xml);
    }

    private void validate(OrderCreatedEvent event){

        if(event.getCustomerId()==null){

            throw new IllegalArgumentException("customerId 없음");

        }

        if(event.getQuantity()<=0){

            throw new IllegalArgumentException("quantity 오류");

        }

    }
}
