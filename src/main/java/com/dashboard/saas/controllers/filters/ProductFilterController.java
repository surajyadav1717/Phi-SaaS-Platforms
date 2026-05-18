package com.dashboard.saas.controllers.filters;

import com.dashboard.saas.dtos.ProductFilterDTO;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.entities.Product;
import com.dashboard.saas.service.filter.ProductFilterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductFilterController {

    private final ProductFilterService productFilterService ;

    public ProductFilterController(ProductFilterService productFilterService) {
        this.productFilterService = productFilterService;
    }


//    @GetMapping("/search/filter/products")
//    public BaseAPIResponse<List<Product>> searchProducts(
//            ProductFilterDTO filter
//    ) {
//
//        List<Product> products =
//                productFilterService.searchProducts(filter, filter.getPage(), filter.getSize());
//
//        BaseAPIResponse<List<Product>> response =
//                new BaseAPIResponse<>(
//                        "Products fetched successfully",
//                        products,
//                        true
//                );
//return response;
//    }


    // Search endpoint
    @GetMapping("/search")
    public BaseAPIResponse<List<Product>> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder   // default DESC
    ) {


        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setProductName(productName);
        filter.setBrand(brand);
        filter.setCategoryId(categoryId);

        List<Product> products = productFilterService.searchProducts(filter, page, size, sortBy, sortOrder);

        return new BaseAPIResponse<>("List Of Products Fetch SuccessFully",products,true);

    }
}
