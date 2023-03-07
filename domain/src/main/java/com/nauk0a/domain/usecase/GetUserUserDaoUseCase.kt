package com.nauk0a.domain.usecase

import com.nauk0a.domain.models.UserDomain
import com.nauk0a.domain.repository.UserRepository

class GetUserUserDaoUseCase (private val userRepository: UserRepository) {

    suspend fun getUserByFirstNameAndLastName(firstName: String, lastName: String):UserDomain? {
        return userRepository.getUserByFirstNameAndLastName(firstName, lastName)
    }

    suspend fun insertUser(user: UserDomain) {
        userRepository.insertUser(user)
    }

    suspend fun isUserExists(firstName: String):Boolean {
        return userRepository.isUserExists(firstName)
    }
}