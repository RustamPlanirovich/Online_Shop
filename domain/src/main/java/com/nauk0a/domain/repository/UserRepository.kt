package com.nauk0a.domain.repository

import com.nauk0a.domain.models.UserDomain

interface UserRepository {

    suspend fun insertUser(user: UserDomain)

    suspend fun getUserByFirstNameAndLastName(
        firstName: String,
        lastName: String,
    ): UserDomain?

    suspend fun isUserExists(firstName: String): Boolean
}