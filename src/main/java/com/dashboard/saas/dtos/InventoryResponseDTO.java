package com.dashboard.saas.dtos;

public class InventoryResponseDTO {


    private Long productId;
    private Integer totalMl;
    private Integer availableMl;

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

    public Integer getAvailableMl() {
        return availableMl;
    }

    public void setAvailableMl(Integer availableMl) {
        this.availableMl = availableMl;
    }
}
