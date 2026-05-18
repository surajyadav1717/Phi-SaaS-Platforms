package com.dashboard.saas.service.filter;

import com.dashboard.saas.dtos.OrderFilterDTO;
import com.dashboard.saas.dtos.OrderResponseDTO;
import com.dashboard.saas.dtos.baseresponse.OrderFilterResponseDTO;

import java.util.List;

public interface OrderFilterService {

    List<OrderFilterResponseDTO> searchOrders(
            OrderFilterDTO filter, int page, int size, String sortBy, String sortDirection
     );


//    List<Product> searchProducts(
//            ProductFilterDTO filter, int page, int size, String sortBy, String sortDirection
//    );
//}
}