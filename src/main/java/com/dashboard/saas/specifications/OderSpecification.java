package com.dashboard.saas.specifications;

import com.dashboard.saas.dtos.OrderFilterDTO;
import com.dashboard.saas.entities.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public  class  OderSpecification {

    public static Specification<Order> filterOrders(OrderFilterDTO orderFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (orderFilterDTO.getQuantity() != null && orderFilterDTO.getQuantity() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("quantity"), orderFilterDTO.getQuantity()));
            }

            if (orderFilterDTO.getTotalMilliliters() != null && orderFilterDTO.getTotalMilliliters() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("totalMl"), orderFilterDTO.getTotalMilliliters()));
            }

            if(orderFilterDTO.getProductId() != null  && orderFilterDTO.getProductId() >= 0) {
                predicates.add(criteriaBuilder.equal(
                        root.get("product").get("id"),
                        orderFilterDTO.getProductId()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}