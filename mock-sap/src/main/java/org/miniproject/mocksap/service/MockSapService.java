package org.miniproject.mocksap.service;

import org.miniproject.mocksap.dto.OrderRequestXml;
import org.miniproject.mocksap.dto.OrderResponseXml;
import org.miniproject.mocksap.entity.SapOrder;
import org.miniproject.mocksap.repository.SapOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MockSapService {

    private final SapOrderRepository repository;

    @Transactional
    public OrderResponseXml createSapOrder(OrderRequestXml request) {

        String sapOrderNumber = generateSapOrderNumber();

        SapOrder sapOrder = SapOrder.builder()
                .sapOrderNumber(sapOrderNumber)
                .customerId(request.getCustomer())
                .productId(request.getProduct())
                .quantity(request.getQty())
                .status("SUCCESS")
                .build();

        repository.save(sapOrder);

        return OrderResponseXml.builder()
                .status("SUCCESS")
                .sapOrder(sapOrderNumber)
                .build();

    }

    /**
     * SAP 주문번호 생성
     */
    private String generateSapOrderNumber() {

        long number = repository.count() + 500001;

        return String.valueOf(number);

    }

}
