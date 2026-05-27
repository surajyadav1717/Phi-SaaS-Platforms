package com.dashboard.saas.service;

import com.dashboard.saas.dtos.CategoryResponseDTO;
import com.dashboard.saas.dtos.ProductRequestDTO;
import com.dashboard.saas.dtos.ProductResponseDTO;
import com.dashboard.saas.entities.Category;
import com.dashboard.saas.entities.Product;
import com.dashboard.saas.exceptions.CategoryNotFoundException;
import com.dashboard.saas.exceptions.ProductAlreadyExistException;
import com.dashboard.saas.repositories.CategoryRepository;
import com.dashboard.saas.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        try {
            Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

            if (productRepository.existsByNameIgnoreCaseAndBrandIgnoreCase(
                    productRequestDTO.getName(), productRequestDTO.getBrand())) {
                throw new ProductAlreadyExistException("Product already exists");
            }

            Product product = new Product();
            product.setName(productRequestDTO.getName());
            product.setBrand(productRequestDTO.getBrand());
            product.setCategory(category);
            // product.setIsActive(true);

            Product saved = productRepository.save(product);

            // MANUAL DTO MAPPING (USING SETTERS)
            ProductResponseDTO response = new ProductResponseDTO();

            response.setId(saved.getId());
            System.out.println(saved.getName()+"saved name");
            response.setName(saved.getName());
            response.setBrand(saved.getBrand());
            response.setCreatedAt(saved.getCreatedAt());

            System.out.println(response + "Product Response");

            // Category mapping
            CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            categoryDTO.setActive(category.getActive());
            response.setCategory(categoryDTO);

            System.out.println(categoryDTO + "category response");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}





