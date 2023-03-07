package com.nauk0a.data.mapper


import com.nauk0a.data.models.CategoryModel
import com.nauk0a.domain.models.CategoryModel as CategoryModelDomain

fun CategoryModel.mapToDomain() = CategoryModelDomain(
    categoryImage = categoryImage, categoryName = categoryName
)