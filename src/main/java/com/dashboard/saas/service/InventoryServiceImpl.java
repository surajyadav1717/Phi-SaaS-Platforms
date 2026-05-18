package com.dashboard.saas.service;

import com.dashboard.saas.dtos.InventoryRequestDTO;
import com.dashboard.saas.dtos.InventoryResponseDTO;
import com.dashboard.saas.entities.Inventory;
import com.dashboard.saas.entities.Product;
import com.dashboard.saas.exceptions.ProductNotFoundException;
import com.dashboard.saas.repositories.InventoryRepository;
import com.dashboard.saas.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final ProductRepository productRepository;
        private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }



    @Override
    public InventoryResponseDTO addOrUpdateInventory(InventoryRequestDTO request) {

//        if (request.getTotalMl() == null || request.getTotalMl() <= 0) {
//            throw new RuntimeException("Total ML must be greater than 0");
//        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Inventory inventory = inventoryRepository.findByProductId(product.getId())
                .orElse(new Inventory());

        inventory.setProduct(product);

        if (inventory.getId() == null) {
            inventory.setTotalMl(request.getTotalMl());
            inventory.setAvailableMl(request.getTotalMl());
            inventory.setCreatedAt(LocalDateTime.now());
        } else {
            int difference = request.getTotalMl() - inventory.getTotalMl();
            int newAvailable = inventory.getAvailableMl() + difference;

            if (newAvailable < 0) {
                throw new RuntimeException("Invalid update: stock cannot be negative");
            }

            inventory.setTotalMl(request.getTotalMl());
            inventory.setAvailableMl(newAvailable);
        }

        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory saved = inventoryRepository.save(inventory);

        InventoryResponseDTO response = new InventoryResponseDTO();
        response.setProductId(saved.getProduct().getId());
        response.setTotalMl(saved.getTotalMl());
        response.setAvailableMl(saved.getAvailableMl());

        return response;
    }



//    //  CORE METHOD (used in OrderHelperForFilter later)
//    @Override
//    public void deductStock(Long productId, Integer usedMl) {
//
//        Inventory inventory = inventoryRepository.findByProductId(productId)
//                .orElseThrow(() -> new RuntimeException("Inventory not found"));
//
//        if (inventory.getAvailableMl() < usedMl) {
//            throw new RuntimeException("Not enough stock");
//        }
//
//        inventory.setAvailableMl(inventory.getAvailableMl() - usedMl);
//        inventory.setUpdatedAt(LocalDateTime.now());
//
//        inventoryRepository.save(inventory);
//    }


        @Override
        @Transactional
        public void deductStock(Long productId, Integer usedMl) {

            if (usedMl == null || usedMl <= 0) {
                throw new RuntimeException("Invalid quantity");
            }

            Inventory inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));
            System.out.println(inventoryRepository.findByProductId(productId)+" inventoryRepository.findByProductId(productId) in deductStock method");

            System.out.println(inventory+" inventory in deductStock method");
            System.out.println(usedMl+" usedMl in deductStock method");


            if (inventory.getAvailableMl() < usedMl) {
                throw new RuntimeException("Not enough stock");
            }
            System.out.println(inventory+" inventory in deductStock method after checking stock");

            inventory.setAvailableMl(inventory.getAvailableMl() - usedMl);
            System.out.println(inventory+" inventory in deductStock method after deducting stock");
            System.out.println(usedMl+" usedMl in deductStock method after deducting stock");
            inventory.setUpdatedAt(LocalDateTime.now());

            inventoryRepository.save(inventory);
            System.out.println(inventory+" inventory in deductStock method after saving inventory");
        }
    }

//    @Override
//    public InventoryResponseDTO getInventory(Long productId) {
//
//        Inventory inventory = inventoryRepository.findByProductId(productId)
//                .orElseThrow(() -> new RuntimeException("Inventory not found"));
//
//        InventoryResponseDTO response = new InventoryResponseDTO();
//        response.setProductId(inventory.getProduct().getId());
//        response.setTotalMl(inventory.getTotalMl());
//        response.setAvailableMl(inventory.getAvailableMl());
//
//        return response;
//    }


