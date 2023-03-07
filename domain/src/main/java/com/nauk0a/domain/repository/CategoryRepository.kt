package com.nauk0a.domain.repository

import com.nauk0a.domain.models.CategoryModel


interface CategoryRepository {
    fun getCategoryList(): List<CategoryModel>
}