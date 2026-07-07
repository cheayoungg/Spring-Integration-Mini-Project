package org.miniproject.shoppingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {

        log.info("INFO LOG");

        System.out.println("SYSTEM OUT");

        return "OK";
    }

}
