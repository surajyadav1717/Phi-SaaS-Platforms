package com.dashboard.saas.service;

import com.dashboard.saas.dtos.ProductRequestDTO;
import com.dashboard.saas.dtos.ProductResponseDTO;

public interface ProductService {

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
}
