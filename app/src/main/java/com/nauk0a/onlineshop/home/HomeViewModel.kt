package com.nauk0a.onlineshop.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauk0a.domain.Item
import com.nauk0a.domain.models.SearchDomain
import com.nauk0a.domain.usecase.GetCategoryListUseCase
import com.nauk0a.domain.usecase.RetrofitUseCase
import com.nauk0a.onlineshop.ProgressCategoryItem
import com.nauk0a.onlineshop.ProgressFlashItem
import com.nauk0a.onlineshop.ProgressLatestAndBrandItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val retrofitUseCase: RetrofitUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    //LiveData хранит данные загруженной фотографии
    private val _selectPhotoUri: MutableLiveData<String> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    var selectPhotoUri: LiveData<String> = _selectPhotoUri

    //Хранит лист категорий
    private val _categoryList: MutableLiveData<List<Item>> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    val categoryList: LiveData<List<Item>> = _categoryList

    //Хранит лист latestList
    private val _latestList: MutableLiveData<List<Item>?> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    val latestList: LiveData<List<Item>?> = _latestList

    //Хранит лист flashList
    private val _flashList: MutableLiveData<List<Item>?> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    val flashList: LiveData<List<Item>?> = _flashList

    //Хранит ошибку которая может вернутся  при запросе latestList и flashList
    private val _errorApiDownloadData: MutableLiveData<String> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    val errorApiDownloadData: LiveData<String> = _errorApiDownloadData

    private val _searchWordsData: MutableLiveData<List<String>?> = MutableLiveData()
    val searchWordsData: LiveData<List<String>?> = _searchWordsData

    fun getSearchWords() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchWordsData.postValue(retrofitUseCase.getSearchWords().getOrNull())
        }
    }

    // Получить URI из SharedPreferences
    fun getUri() {
        _selectPhotoUri.value = sharedPreferences.getString("Uri", "")
    }

    //Запрашиваем лист с категориями
    fun getCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryList.postValue(IntRange(1, 6).map { ProgressCategoryItem })
            delay(2000L)
            _categoryList.postValue(getCategoryListUseCase.getCategoryList())
        }
    }

    fun showData() {
        // Запускаем корутину для загрузки данных из источников
        viewModelScope.launch(Dispatchers.IO) {
            //В этимх переменных находится ответы возвращенные при запросе к API
            val (latestResult, flashSaleResult)
                    = retrofitUseCase.getApiData()
            //Проверяем что оба запроса завершились успешно
            if (latestResult.isSuccess && flashSaleResult.isSuccess) {
                _latestList.postValue(IntRange(1, 4).map { ProgressLatestAndBrandItem })
                _flashList.postValue(IntRange(1, 2).map { ProgressFlashItem })
                delay(2000)
                _latestList.postValue(latestResult.getOrNull())
                _flashList.postValue(flashSaleResult.getOrNull())
            } else {
                _errorApiDownloadData.postValue("Не удалось загрузить данные")
            }
        }
    }
}