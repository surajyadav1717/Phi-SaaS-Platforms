package com.dashboard.saas.controllers;

import com.dashboard.saas.dtos.*;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.service.InventoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

        private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Add / Update Inventory
        @PostMapping("/add-or-update")
        public BaseAPIResponse<InventoryResponseDTO> addOrUpdateInventory(
                @RequestBody InventoryRequestDTO request) {

            InventoryResponseDTO response = inventoryService.addOrUpdateInventory(request);
            return new BaseAPIResponse<>("Inventory added/updated successfully",response, true);
        }

    }
