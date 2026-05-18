package com.dashboard.saas.dtos;

public class OrderFilterDTO {

    private Integer productId;

    private long variantId;

    private Integer   quantity;

    private Integer totalMilliliters;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public long getVariantId() {
        return variantId;
    }

    public void setVariantId(long variantId) {
        this.variantId = variantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalMilliliters() {
        return totalMilliliters;
    }

    public void setTotalMilliliters(Integer totalMilliliters) {
        this.totalMilliliters = totalMilliliters;
    }
}
