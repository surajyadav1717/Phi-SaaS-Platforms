package com.dashboard.saas.controllers;

import com.dashboard.saas.dtos.ProductVariantBulkRequestDTO;
import com.dashboard.saas.dtos.ProductVariantResponseDTO;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.service.ProductVariantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductVariantController {


    private final ProductVariantService variantService;

    public ProductVariantController(ProductVariantService variantService) {
        this.variantService = variantService;
    }

    @PostMapping("/{productId}/variants")
    public BaseAPIResponse<List<ProductVariantResponseDTO>> addVariants(
            @PathVariable Long productId,
            @RequestBody ProductVariantBulkRequestDTO request) {

        List<ProductVariantResponseDTO> productVariantResponseDTOS = variantService.addVariants(productId, request.getVariants());

        return new BaseAPIResponse<>( "Product variants added successfully",productVariantResponseDTOS, true);
    }


}
