package com.nauk0a.onlineshop.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauk0a.domain.models.CategoryModel
import com.nauk0a.domain.models.FlashSaleModelDomain
import com.nauk0a.domain.models.LatestModelDomain
import com.nauk0a.domain.usecase.GetCategoryListUseCase
import com.nauk0a.domain.usecase.RetrofitUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val retrofitUseCase: RetrofitUseCase,
) : ViewModel() {

    //Хранит лист категорий
    private val _categoryList: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    val categoryList: LiveData<List<CategoryModel>> = _categoryList

    //Хранит лист latestList
    private val _latestList: MutableLiveData<List<LatestModelDomain>?> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    val latestList: LiveData<List<LatestModelDomain>?> = _latestList

    //Хранит лист flashList
    private val _flashList: MutableLiveData<List<FlashSaleModelDomain>?> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    val flashList: LiveData<List<FlashSaleModelDomain>?> = _flashList

    //Хранит ошибку которая может вернутся  при запросе latestList и flashList
    private val _errorApiDownloadData: MutableLiveData<String> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    val errorApiDownloadData: LiveData<String> = _errorApiDownloadData


    //Запрашиваем лист с категориями
    fun getCategoryList() {
        viewModelScope.launch {
            _categoryList.postValue(getCategoryListUseCase.getCategoryList())
        }
    }

    fun showData() {
        // Запускаем корутину для загрузки данных из источников
        viewModelScope.launch {
            //В этимх переменных находится ответы возвращенные при запросе к API
            val (latestResult, flashSaleResult)
                    = retrofitUseCase.getApiData()
            //Проверяем что оба запроса завершились успешно
            if (latestResult.isSuccess && flashSaleResult.isSuccess){
                _latestList.postValue(latestResult.getOrNull())
                _flashList.postValue(flashSaleResult.getOrNull())
            }else{
                _errorApiDownloadData.postValue("Не удалось загрузить данные")
            }
        }
    }
}