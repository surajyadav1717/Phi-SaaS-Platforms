package com.dashboard.saas.service;

import com.dashboard.saas.dtos.ProductVariantRequestDTO;
import com.dashboard.saas.dtos.ProductVariantResponseDTO;

import java.util.List;

public interface ProductVariantService {


    List<ProductVariantResponseDTO> addVariants(Long productId, List<ProductVariantRequestDTO> request);


}
