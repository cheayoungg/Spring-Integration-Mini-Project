package org.miniproject.integrationservice.mapper;

import org.miniproject.integrationservice.dto.SapOrderRequest;
import org.miniproject.integrationservice.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

// kafka event -> sap dto
// kafka event 받아서 sap request으로 리턴
@Component
public class OrderMapper {

    public SapOrderRequest toSapRequest(OrderCreatedEvent event){

        return SapOrderRequest.builder()
                .customer(event.getCustomerId())
                .product(event.getProductId())
                .qty(event.getQuantity())
                .build();

    }
}
