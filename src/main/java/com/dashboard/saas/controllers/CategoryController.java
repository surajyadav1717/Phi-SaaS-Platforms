package com.dashboard.saas.controllers;


import com.dashboard.saas.dtos.*;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.service.CategoryService;
import com.dashboard.saas.service.ProductService;
import com.dashboard.saas.service.ProductVariantService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductVariantService variantService ;

    public CategoryController(CategoryService categoryService, ProductService productService, ProductVariantService variantService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.variantService = variantService;
    }

    @PostMapping("/create")
    public BaseAPIResponse<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO request) {

        CategoryResponseDTO response = categoryService.createCategory(request);
        return new BaseAPIResponse<>("Category created successfully",response, true);
    }


    @PostMapping("/create-product-category")
    public BaseAPIResponse<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request) {

        ProductResponseDTO response = productService.createProduct(request);
        return new BaseAPIResponse<>("Product created successfully",response, true);
    }


}
