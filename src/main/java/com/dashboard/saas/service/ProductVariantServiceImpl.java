package com.dashboard.saas.service;

import com.dashboard.saas.dtos.ProductVariantRequestDTO;
import com.dashboard.saas.dtos.ProductVariantResponseDTO;
import com.dashboard.saas.entities.Product;
import com.dashboard.saas.entities.ProductVariant;
import com.dashboard.saas.exceptions.ProductNotFoundException;
import com.dashboard.saas.exceptions.VariantAlreadyExistForML;
import com.dashboard.saas.repositories.ProductRepository;
import com.dashboard.saas.repositories.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService{

    private final ProductService productService;
    private final ProductRepository productRepository;
    private  final ProductVariantRepository variantRepository;


    public ProductVariantServiceImpl(ProductService productService, ProductRepository productRepository, ProductVariantRepository variantRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }


    @Override
    public List<ProductVariantResponseDTO> addVariants(Long productId, List<ProductVariantRequestDTO> request) {

        try {


            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            List<ProductVariant> variants = new ArrayList<>();

            for (ProductVariantRequestDTO variantRequest : request) {

                // Prevent duplicate ML
                if (variantRepository.existsByProductIdAndVolumeMl(productId, variantRequest.getVolumeMl())) {
                    throw new VariantAlreadyExistForML("Variant already exists for ml: " + variantRequest.getVolumeMl());
                }

                ProductVariant variant = new ProductVariant();
                variant.setProduct(product);
                variant.setVolumeMl(variantRequest.getVolumeMl());
                variant.setPrice(variantRequest.getPrice());
                variants.add(variant);
            }

            List<ProductVariant> saved = variantRepository.saveAll(variants);

            return saved.stream().map(v -> {
                ProductVariantResponseDTO dto = new ProductVariantResponseDTO();
                dto.setId(v.getId());
                dto.setVolumeMl(v.getVolumeMl());
                dto.setPrice(v.getPrice());
                dto.setActive(v.getActive());
                return dto;
            }).toList();


    }     catch (ProductNotFoundException | VariantAlreadyExistForML e) {
            throw e; // Rethrow the exception to be handled by the global exception handler
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while adding variants: " + e.getMessage(), e);
        }
    }
}

