package com.nauk0a.onlineshop.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauk0a.domain.models.UserDomain
import com.nauk0a.data.repositroy.UserRepositoryImpl
import com.nauk0a.domain.usecase.GetUserUserDaoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val userDaoUseCase: GetUserUserDaoUseCase,
) : ViewModel() {

    //Хранит возвращенного пользователя если он существует в базе данных
    private val _userExists: MutableLiveData<UserDomain> = MutableLiveData()
    //Это для того чтобы нельзя было изменять извне
    val userExists: LiveData<UserDomain> = _userExists

    //Метод для проверки существования пользователя в базе данных
    fun isUserExists(firstName: String, lastName: String) {
        viewModelScope.launch {
            _userExists.postValue(userDaoUseCase.getUserByFirstNameAndLastName(firstName, lastName))
        }
    }

}