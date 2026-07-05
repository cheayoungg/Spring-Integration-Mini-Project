package org.miniproject.shoppingservice.controller;

import org.miniproject.shoppingservice.dto.OrderRequest;
import org.miniproject.shoppingservice.dto.OrderResponse;
import org.miniproject.shoppingservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request
    ) {

        return orderService.createOrder(request);

    }
}
