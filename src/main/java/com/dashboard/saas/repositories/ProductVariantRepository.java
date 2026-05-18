package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.Category;
import com.dashboard.saas.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    boolean existsByProductIdAndVolumeMl(Long productId,Integer volumeMl);
}
