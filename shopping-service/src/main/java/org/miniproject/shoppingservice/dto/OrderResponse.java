package org.miniproject.shoppingservice.dto;

import org.miniproject.shoppingservice.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private OrderStatus status;
}
