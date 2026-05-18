package com.dashboard.saas.service;

import com.dashboard.saas.dtos.InventoryRequestDTO;
import com.dashboard.saas.dtos.InventoryResponseDTO;

public interface InventoryService {

    public InventoryResponseDTO addOrUpdateInventory(InventoryRequestDTO request);

    void deductStock(Long productId, Integer usedMl);


    // public InventoryResponseDTO getInventoryByProductId(Long productId);
}
