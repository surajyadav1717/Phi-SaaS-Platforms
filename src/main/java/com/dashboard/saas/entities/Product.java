package com.dashboard.saas.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "product_name", nullable = false)
        private String name;

        @Column(name = "brand")
        private String brand;

        @ManyToOne //
        @JoinColumn(name = "category_id")
        private Category category;

        @CreationTimestamp
        @Column(name="created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

         @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        public Product() {
        }

        public Product(String name, String brand, Category category) {
            this.name = name;
            this.brand = brand;
            this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}



