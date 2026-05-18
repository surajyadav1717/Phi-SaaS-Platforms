package com.dashboard.saas.service.filter;

import com.dashboard.saas.dtos.ProductFilterDTO;
import com.dashboard.saas.entities.Product;
import com.dashboard.saas.repositories.ProductRepository;
import com.dashboard.saas.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductFilterServiceImpl implements ProductFilterService {

    private final ProductRepository productRepository;

    public ProductFilterServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> searchProducts(ProductFilterDTO filter, int page, int size, String sortBy, String sortOrder) {

        // 1. Build the Specification
        Specification<Product> specification = ProductSpecification.filterProducts(filter);

        //  Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort.Direction direction =
                "asc".equalsIgnoreCase(sortOrder)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        // 2. Create Pageable with sorting
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "id")
        );

        // 3. Fetch paged results
        Page<Product> productPage = productRepository.findAll(specification, pageable);


        // 4. Convert to List
        return productPage.getContent();



    }
}


