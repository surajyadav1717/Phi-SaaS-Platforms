package com.dashboard.saas.service.filter;

import com.dashboard.saas.dtos.ProductFilterDTO;
import com.dashboard.saas.entities.Product;

import java.util.List;

public interface ProductFilterService {

    List<Product> searchProducts(
            ProductFilterDTO filter,int page,int size,String sortBy, String sortDirection
    );
}
