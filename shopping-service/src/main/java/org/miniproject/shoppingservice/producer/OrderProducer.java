package org.miniproject.shoppingservice.producer;

import org.miniproject.shoppingservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publish(OrderCreatedEvent event) {

        log.info("Kafka 발행 시작 - orderId={}", event.getOrderId());

        kafkaTemplate.send(
                TOPIC,
                event.getOrderId().toString(),
                event
        );

    }
}
