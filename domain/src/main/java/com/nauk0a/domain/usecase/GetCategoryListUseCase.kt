package com.nauk0a.domain.usecase

import com.nauk0a.domain.models.CategoryModel
import com.nauk0a.domain.repository.CategoryRepository

class GetCategoryListUseCase(private val categoryRepository: CategoryRepository) {
    fun getCategoryList():List<CategoryModel>{
        return categoryRepository.getCategoryList()
    }
}