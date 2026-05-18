package com.dashboard.saas.service;


import com.dashboard.saas.dtos.OrderRequestDTO;
import com.dashboard.saas.dtos.OrderResponseDTO;
import com.dashboard.saas.entities.Order;
import com.dashboard.saas.entities.Product;
import com.dashboard.saas.entities.ProductVariant;
import com.dashboard.saas.exceptions.ProductNotFoundException;
import com.dashboard.saas.exceptions.VariantDoesNotBelongToProduct;
import com.dashboard.saas.exceptions.VariantNotFoundException;
import com.dashboard.saas.repositories.OrderRepository;
import com.dashboard.saas.repositories.ProductRepository;
import com.dashboard.saas.repositories.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;

    public OrderServiceImpl(InventoryService inventoryService, OrderRepository orderRepository, ProductRepository productRepository, ProductVariantRepository variantRepository) {
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        ProductVariant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new VariantNotFoundException("Variant not found"));

        if (!variant.getProduct().getId().equals(product.getId())) {
            throw new VariantDoesNotBelongToProduct("Variant does not belong to product");
        }
        int totalMl = variant.getVolumeMl() * request.getQuantity();

        inventoryService.deductStock(product.getId(), totalMl);

        BigDecimal totalPrice = variant.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = new Order();
        order.setProduct(product);
        order.setVariant(variant);
        order.setQuantity(request.getQuantity());
        order.setTotalMilliliters(totalMl);
        order.setTotalPrice(totalPrice);

        Order saved = orderRepository.save(order);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(saved.getId());
        response.setProductId(product.getId());
        response.setVariantId(variant.getId());
        response.setQuantity(saved.getQuantity());
        response.setTotalMilliliters(saved.getTotalMilliliters());
        response.setTotalPrice(saved.getTotalPrice());
        response.setProductName(product.getName());
        response.setVolumeMl(variant.getVolumeMl());
        response.setUnitPrice(variant.getPrice());
        response.setBrandName(product.getBrand());
        return response;
    }

}




