package com.dashboard.saas.dtos;

import jakarta.validation.constraints.NotNull;

public class InventoryRequestDTO {

    private Long productId;

    @NotNull(message = "Total ML must be greater than 0")
    private Integer totalMl;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getTotalMl() {
        return totalMl;
    }

    public void setTotalMl(Integer totalMl) {
        this.totalMl = totalMl;
    }
}
