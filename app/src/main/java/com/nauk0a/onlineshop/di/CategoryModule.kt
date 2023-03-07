package com.nauk0a.onlineshop.di

import com.nauk0a.data.local.CategoryList
import com.nauk0a.data.repositroy.CategoryRepositoryImpl
import com.nauk0a.domain.repository.CategoryRepository
import com.nauk0a.domain.usecase.GetCategoryListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CategoryModule {
    @Singleton
    @Provides
    fun provideCategoryList(): CategoryList {
        return CategoryList()
    }

    @Provides
    fun provideCategoryRepository(categoryList: CategoryList): CategoryRepository {
        return CategoryRepositoryImpl(categoryList)
    }

    @Provides
    fun provideCategoryUseCase(categoryRepositoryImpl: CategoryRepositoryImpl): GetCategoryListUseCase {
        return GetCategoryListUseCase(categoryRepositoryImpl)
    }
}