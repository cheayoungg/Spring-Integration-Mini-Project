package org.miniproject.mocksap.controller;

import org.miniproject.mocksap.dto.OrderRequestXml;
import org.miniproject.mocksap.dto.OrderResponseXml;
import org.miniproject.mocksap.service.MockSapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sap/orders")
public class MockSapController {

    private final MockSapService service;

    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public OrderResponseXml createOrder(
            @RequestBody OrderRequestXml request
    ) {

        return service.createSapOrder(request);

    }

}
