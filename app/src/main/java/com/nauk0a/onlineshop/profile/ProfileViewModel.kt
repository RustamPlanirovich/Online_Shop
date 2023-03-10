package com.nauk0a.onlineshop.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauk0a.domain.models.UserDomain
import com.nauk0a.domain.usecase.GetUserUserDaoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userDaoUseCase: GetUserUserDaoUseCase,
) : ViewModel() {

    //LiveData хранит данные загруженной фотографии
    private val _selectPhotoUri: MutableLiveData<String> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    var selectPhotoUri: LiveData<String> = _selectPhotoUri

    //LiveData хранит теущего пользователя
    private val _currentUser: MutableLiveData<UserDomain> = MutableLiveData()

    //Это для того чтобы нельзя было изменять извне
    var currentUser: LiveData<UserDomain> = _currentUser

    // Получить URI из SharedPreferences
    fun getUri() {
        _selectPhotoUri.value = sharedPreferences.getString("Uri", "")
    }

    // Запись URI в SharedPreferences
    fun setUri(uri: String) {
        sharedPreferences.edit().putString("Uri", uri).apply()
    }

    fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentUser.postValue(userDaoUseCase.getUserName(getAuthorizedUserName()!!))
        }
    }

    fun getAuthorizedUserName(): String? {
        return sharedPreferences.getString("userName", "")
    }
}