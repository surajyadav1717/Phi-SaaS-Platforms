package com.dashboard.saas.service;

import com.dashboard.saas.dtos.CategoryRequestDTO;
import com.dashboard.saas.dtos.CategoryResponseDTO;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO request);

}
