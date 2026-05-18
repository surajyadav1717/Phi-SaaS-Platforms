package com.dashboard.saas.specifications;

import com.dashboard.saas.dtos.ProductFilterDTO;
import com.dashboard.saas.entities.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterProducts(ProductFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Product Name filter
            if (filter.getProductName() != null && !filter.getProductName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + filter.getProductName().toLowerCase() + "%"
                ));
            }

            // Brand filter
            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("brand")),
                        "%" + filter.getBrand().toLowerCase() + "%"
                ));
            }

            // Category filter
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(
                        root.get("category").get("id"),
                        filter.getCategoryId()
                ));
            }



//            // Active filter
//            if (filter.getActive() != null) {
//                predicates.add(cb.equal(
//                        root.get("active"),
//                        filter.getActive()
//                ));
//            }

            // Combine all predicates with AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
