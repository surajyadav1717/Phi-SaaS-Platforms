package com.dashboard.saas.service;

import com.dashboard.saas.dtos.OrderRequestDTO;
import com.dashboard.saas.dtos.OrderResponseDTO;

public interface OrderService {

    public OrderResponseDTO createOrder(OrderRequestDTO request);
}
