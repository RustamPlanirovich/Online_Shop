package com.nauk0a.data.local

import com.nauk0a.data.R
import com.nauk0a.data.models.CategoryModel

class CategoryList {

    //Формируем список категорий товара
    fun getCategoryList():List<CategoryModel>{
        return listOf(
            CategoryModel("phone_category","Phones"),
            CategoryModel("headphones_category","Headphones"),
            CategoryModel("games_category", "Games"),
            CategoryModel("cars_category","Cars"),
            CategoryModel("furniture_category", "Furniture"),
            CategoryModel("kids_category","Kids")
        )
    }
}