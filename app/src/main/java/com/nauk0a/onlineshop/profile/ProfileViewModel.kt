package com.nauk0a.onlineshop.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    //LiveData хранит данные загруженной фотографии
    private val _selectPhotoUri: MutableLiveData<String> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    var selectPhotoUri: LiveData<String> = _selectPhotoUri

    // Получить URI из SharedPreferences
    fun getUri() {
        _selectPhotoUri.value = sharedPreferences.getString("Uri", "")
    }

    // Запись URI в SharedPreferences
    fun setUri(uri: String){
        sharedPreferences.edit().putString("Uri", uri).apply()
    }
}