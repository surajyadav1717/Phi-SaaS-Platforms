package com.dashboard.saas.dtos;

import com.dashboard.saas.entities.Category;
import jakarta.validation.constraints.NotBlank;

public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    private String brand;

    @NotBlank(message = "Category is required")
    private Long categoryId;

    public @NotBlank(message = "Category is required") Long getCategoryId() {
        return categoryId;
    }

    public @NotBlank(message = "Product name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Product name is required") String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }



    public void setCategoryId(@NotBlank(message = "Category is required") Long categoryId) {
        this.categoryId = categoryId;
    }
}
