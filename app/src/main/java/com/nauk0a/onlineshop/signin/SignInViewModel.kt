package com.nauk0a.onlineshop.signin

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauk0a.domain.models.UserDomain
import com.nauk0a.data.repositroy.UserRepositoryImpl
import com.nauk0a.domain.usecase.GetUserUserDaoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userDaoUseCase: GetUserUserDaoUseCase,
) : ViewModel() {

    //LiveData хранит Boolean имеется ли пользователь с данным firstName в базу данных
    private val _userExists: MutableLiveData<Boolean> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    val userExists: LiveData<Boolean> = _userExists

    //Метод добавления новго пользователя
    fun addNewUser(user: UserDomain) {
        viewModelScope.launch {
            userDaoUseCase.insertUser(user = user)
        }
    }
    //Метод проверки существования пользователя по данному firstName
    fun isUserExists(firstName: String) {
        viewModelScope.launch {
            _userExists.postValue(userDaoUseCase.isUserExists(firstName))
        }
    }

    fun saveUserName(userName:String){
        sharedPreferences.edit().putString("userName", userName).apply()
    }

}