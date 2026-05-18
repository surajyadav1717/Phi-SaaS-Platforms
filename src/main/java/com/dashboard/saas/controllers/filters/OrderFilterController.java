package com.dashboard.saas.controllers.filters;

import com.dashboard.saas.dtos.OrderFilterDTO;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.dtos.baseresponse.OrderFilterResponseDTO;
import com.dashboard.saas.service.filter.OrderFilterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderFilterController {

    private final OrderFilterService orderFilterService;

    public OrderFilterController(OrderFilterService orderFilterService) {
        this.orderFilterService = orderFilterService;
    }

    // Search endpoint
    @GetMapping("/search")
    public BaseAPIResponse<List<OrderFilterResponseDTO>> searchOrders(
            @RequestParam(required = false) Integer productId,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer totalMilliliters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder
    ) {
        // 1 Build the filter DTO
        OrderFilterDTO filter = new OrderFilterDTO();
        filter.setQuantity(quantity);
        filter.setTotalMilliliters(totalMilliliters);
        filter.setProductId(productId);  // optional

        // 2 Fetch filtered orders
        List<OrderFilterResponseDTO> orders = orderFilterService.searchOrders(filter, page, size, sortBy, sortOrder);

        // 3 Wrap in API response
        return new BaseAPIResponse<>("List Of Orders Fetch SuccessFully",orders,true);


    }
}