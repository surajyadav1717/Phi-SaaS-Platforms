package com.dashboard.saas.helper;

import com.dashboard.saas.dtos.baseresponse.OrderFilterResponseDTO;
import com.dashboard.saas.entities.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class OrderHelperForFilter {


    public static @NotNull OrderFilterResponseDTO getOrderFilterResponseDTO(Order order) {

        OrderFilterResponseDTO orderFilterResponseDTO = new OrderFilterResponseDTO();

        orderFilterResponseDTO.setOrderId(order.getId());

        orderFilterResponseDTO.setProductId(order.getProduct().getId());

        orderFilterResponseDTO.setProductName(order.getProduct().getName());

        orderFilterResponseDTO.setBrandName(order.getProduct().getBrand());

        orderFilterResponseDTO.setVariantId(order.getVariant().getId());

        orderFilterResponseDTO.setVolumeMl(order.getVariant().getVolumeMl());

        orderFilterResponseDTO.setUnitPrice(order.getVariant().getPrice());

        orderFilterResponseDTO.setQuantity(order.getQuantity());

        orderFilterResponseDTO.setTotalMilililiters(order.getTotalMilliliters());

        orderFilterResponseDTO.setTotalPrice(order.getTotalPrice());

        return orderFilterResponseDTO;
    }

}
