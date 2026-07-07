package org.miniproject.integrationservice.consumer;
import org.miniproject.integrationservice.event.OrderCreatedEvent;
import org.miniproject.integrationservice.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final IntegrationService integrationService;

    @KafkaListener(
            topics = "order-created",
            groupId = "order-sap-group"
    )
    /*public void consume(String message) {
        System.out.println("받은 메시지 = " + message);
    }*/
    public void consume(OrderCreatedEvent event) {

        log.info("Kafka Message Received");

        integrationService.process(event);
    }

}
