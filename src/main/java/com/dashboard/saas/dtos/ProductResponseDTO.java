package com.dashboard.saas.dtos;

import java.time.LocalDateTime;

public class ProductResponseDTO {

    private Long id;

    private String name;

    private String brand;


    private CategoryResponseDTO category;

    private LocalDateTime createdAt;



    public ProductResponseDTO(Long id, String name, String brand, CategoryResponseDTO category, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.brand = brand;

        this.category = category;
        this.createdAt = createdAt;
    }


    public ProductResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }



    public CategoryResponseDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDTO category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
