package com.dashboard.saas.service.filter;

import com.dashboard.saas.dtos.OrderFilterDTO;
import com.dashboard.saas.dtos.OrderResponseDTO;
import com.dashboard.saas.dtos.baseresponse.OrderFilterResponseDTO;
import com.dashboard.saas.entities.Order;
import com.dashboard.saas.helper.OrderHelperForFilter;
import com.dashboard.saas.repositories.OrderRepository;
import com.dashboard.saas.specifications.OderSpecification;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderFilterServiceImpl implements OrderFilterService {

    private final OrderRepository orderRepository;
    private final OrderHelperForFilter orderHelperForFilter; // <-- required bean


    public OrderFilterServiceImpl(OrderRepository orderRepository, OrderHelperForFilter orderHelperForFilter, OrderHelperForFilter orderHelperForFilter1) {
        this.orderRepository = orderRepository;
        this.orderHelperForFilter = orderHelperForFilter1;
    }

    @Override
    public List<OrderFilterResponseDTO> searchOrders(OrderFilterDTO filter, int page, int size, String sortBy, String sortDirection) {

        // 1. Build the Specification
        Specification<Order> specification = OderSpecification.filterOrders(filter);

        //sort direction here

//       Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort.Direction direction =
                "asc".equalsIgnoreCase(sortDirection)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;


        // pageable for page size  limit

        // 2. Create Pageable with sorting
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "id")
        );


        // 3. Fetch paged results
        Page<Order> orderPage = orderRepository.findAll(specification, pageable);


        //  4. Convert to List
//        return productPage.getContent();

        List<OrderFilterResponseDTO> responseList = new ArrayList<>();

        for (Order order : orderPage.getContent()) {

            // I have called helper
            OrderFilterResponseDTO dto = OrderHelperForFilter.getOrderFilterResponseDTO(order);

            responseList.add(dto);
        }

        return responseList;
    }


}


//    @Override
//    public List<Product> searchProducts(ProductFilterDTO filter, int page, int size, String sortBy, String sortOrder) {
//
//        // 1. Build the Specification
//        Specification<Product> specification = ProductSpecification.filterProducts(filter);
//
//        //  Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
//
//
//        // 2. Create Pageable with sorting
//        Pageable pageable = PageRequest.of(
//                page,
//                size,
//                Sort.by(Sort.Direction.ASC, "id")
//        );
//
//        // 3. Fetch paged results
//        Page<Product> productPage = productRepository.findAll(specification, pageable);
//
//
//        // 4. Convert to List
//        return productPage.getContent();
//
//
//
//    }

//    }
//}
