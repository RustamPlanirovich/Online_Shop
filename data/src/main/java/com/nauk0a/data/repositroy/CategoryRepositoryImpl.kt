package com.nauk0a.data.repositroy

import com.nauk0a.data.local.CategoryList
import com.nauk0a.data.mapper.mapToDomain
import com.nauk0a.domain.models.CategoryModel
import com.nauk0a.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryList: CategoryList) :
    CategoryRepository {
    //Запрашиваем список категорий
    override fun getCategoryList(): List<CategoryModel> {
        return categoryList.getCategoryList().map { it.mapToDomain() }
    }
}