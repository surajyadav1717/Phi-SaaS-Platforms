package com.dashboard.saas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_variant")
public class ProductVariant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "volume_ml", nullable = false)
    private Integer volumeMl;

    private BigDecimal price;

    private Boolean isActive = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne // Many variants belong to one product
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;


    public ProductVariant() {
    }

    public ProductVariant(Integer volumeMl, BigDecimal price, Boolean isActive, LocalDateTime createdAt, Product product) {
        this.volumeMl = volumeMl;
        this.price = price;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(Integer volumeMl) {
        this.volumeMl = volumeMl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
