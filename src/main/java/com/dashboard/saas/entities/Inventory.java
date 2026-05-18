package com.dashboard.saas.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne //one product can have only one inventory record
        @JoinColumn(name = "product_id", unique = true)
        private Product product;

        private Integer totalMl;

        private Integer availableMl;

        private LocalDateTime createdAt = LocalDateTime.now();

        private LocalDateTime updatedAt;

        public Inventory() {
        }

        public Inventory(Product product, Integer totalMl, Integer availableMl) {
            this.product = product;
            this.totalMl = totalMl;
            this.availableMl = availableMl;
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Inventory setAvailableMl(Integer availableMl) {
        this.availableMl = availableMl;
        return this;
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

