package com.dashboard.saas.service;

import com.dashboard.saas.dtos.CategoryRequestDTO;
import com.dashboard.saas.dtos.CategoryResponseDTO;
import com.dashboard.saas.entities.Category;
import com.dashboard.saas.exceptions.CategoryAlreadyExistException;
import com.dashboard.saas.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl  implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {


            if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
                throw new CategoryAlreadyExistException("Category already exists");
            }

            Category category = new Category();
            category.setName(request.getName());
            category.setDescription(request.getDescription());
            //category.setActive(request.isActive());

            categoryRepository.save(category);
            return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription(), category.getActive());

    }
}
