package org.miniproject.shoppingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.miniproject.shoppingservice.entity.Order;
import org.miniproject.shoppingservice.entity.OrderStatus;
import org.miniproject.shoppingservice.repository.OrderRepository;
import org.miniproject.shoppingservice.event.OrderCreatedEvent;
import org.miniproject.shoppingservice.producer.OrderProducer;
import org.miniproject.shoppingservice.dto.OrderRequest;
import org.miniproject.shoppingservice.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        log.info("주문 생성 요청 - customerId={}, productId={}, quantity={}",
                request.getCustomerId(),
                request.getProductId(),
                request.getQuantity());

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(OrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);

        log.info("DB 저장 완료 - orderId={}", savedOrder.getId());

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .customerId(savedOrder.getCustomerId())
                .productId(savedOrder.getProductId())
                .quantity(savedOrder.getQuantity())
                .build();

        orderProducer.publish(event);

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .status(savedOrder.getStatus())
                .build();

    }
}
