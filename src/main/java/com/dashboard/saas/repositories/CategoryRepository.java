package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);

    @NotNull
    Optional<Category> findById(Long id );

    boolean existsByNameIgnoreCase(String name);
}
