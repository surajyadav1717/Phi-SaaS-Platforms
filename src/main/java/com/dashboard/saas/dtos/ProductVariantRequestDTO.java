package com.dashboard.saas.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductVariantRequestDTO {

    @NotNull(message = "Volume in ml is required")
    private Integer volumeMl;

    @NotNull(message = "Price is required In INR In Digits")
    private BigDecimal price;


    public ProductVariantRequestDTO() {
    }


    public ProductVariantRequestDTO(Integer volumeMl, BigDecimal price) {
        this.volumeMl = volumeMl;
        this.price = price;
    }


    public @NotNull(message = "Volume in ml is required") Integer getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(@NotNull(message = "Volume in ml is required") Integer volumeMl) {
        this.volumeMl = volumeMl;
    }

    public @NotNull(message = "Price is required In INR In Digits") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required In INR In Digits") BigDecimal price) {
        this.price = price;
    }
}
