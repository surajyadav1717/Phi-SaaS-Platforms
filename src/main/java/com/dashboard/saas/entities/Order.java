package com.dashboard.saas.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Integer totalMilliliters;

    private LocalDateTime createdAt = LocalDateTime.now();


    public Order(Integer totalMilliliters) {
        this.totalMilliliters = totalMilliliters;
    }

    public Order(Long id, Product product, ProductVariant variant, Integer quantity, BigDecimal totalPrice, Integer totalMl, Integer totalMilililiters, LocalDateTime createdAt) {
        this.id = id;
        this.product = product;
        this.variant = variant;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.totalMilliliters = totalMilililiters;
        this.createdAt = createdAt;
    }

    public Order() {

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

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalMilliliters() {
        return totalMilliliters;
    }

    public void setTotalMilliliters(Integer totalMilliliters) {
        this.totalMilliliters = totalMilliliters;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
