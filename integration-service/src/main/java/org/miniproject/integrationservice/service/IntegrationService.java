package org.miniproject.integrationservice.service;
import org.miniproject.integrationservice.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntegrationService {
    public void process(OrderCreatedEvent event) {

        log.info("====================================");
        log.info("Order Event Received");
        log.info("orderId = {}", event.getOrderId());
        log.info("customerId = {}", event.getCustomerId());
        log.info("productId = {}", event.getProductId());
        log.info("quantity = {}", event.getQuantity());
        log.info("====================================");

    }
}
