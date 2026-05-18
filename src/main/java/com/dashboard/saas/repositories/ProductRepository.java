package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {

    boolean existsByNameIgnoreCaseAndBrandIgnoreCase(String name, String brand);

    @NotNull
    Optional<Product> findById(Long id);

   // Optional<Product> findBySku(String sku);

  //  List<Product> findByIsActiveTrue();
}
