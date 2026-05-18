package com.dashboard.saas.controllers;

import com.dashboard.saas.dtos.OrderRequestDTO;
import com.dashboard.saas.dtos.OrderResponseDTO;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create/order")
    public BaseAPIResponse<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO response = orderService.createOrder(request);

        System.out.println(response+"order response--------------------------");
        return new BaseAPIResponse<>( "Order Placed Successfully",response, true);
    }

}
