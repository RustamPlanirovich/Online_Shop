package com.nauk0a.data.repositroy

import com.nauk0a.data.local.UserDao
import com.nauk0a.data.mapper.mapToDomain
import com.nauk0a.data.models.User
import com.nauk0a.domain.models.UserDomain
import com.nauk0a.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    //Метод для вставки объекта User в базу данных
    override suspend fun insertUser(user: UserDomain) {
        userDao.insert(
            User(
                id = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email
            )
        )
    }


    //Метод для поиска объекта User в базе данных по полю firstName и lastName
    override suspend fun getUserByFirstNameAndLastName(
        firstName: String,
        lastName: String,
    ): UserDomain? {
        return userDao.getUserByFirstNameAndLastName(firstName, lastName)?.mapToDomain()
    }

    //Метод для поиска объекта User в базе данных по полю firstName
    override suspend fun isUserExists(firstName: String): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getUserByFirstName(firstName) != null
        }
    }


}