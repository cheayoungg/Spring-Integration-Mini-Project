package org.miniproject.shoppingservice.producer;

import org.miniproject.shoppingservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {
    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publish(OrderCreatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getOrderId().toString(),
                event
        );

    }
}
