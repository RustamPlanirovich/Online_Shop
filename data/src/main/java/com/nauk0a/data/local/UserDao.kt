package com.nauk0a.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nauk0a.data.models.User
import com.nauk0a.domain.models.UserDomain

@Dao
interface UserDao {

    /** Запрос для вставки объекта User в базу данных
     * если конфликт вставки - просто заменяем */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: com.nauk0a.data.models.User)

    //Запрос для поиска объекта User в базе данных по полю firstName
    @Query("SELECT * FROM user WHERE firstName = :firstName")
    suspend fun getUserByFirstName(firstName: String): User?

    /**Запрос для поиска объекта User в базе данных по полю firstName и lastName
     * Это используется для входа пользователя в приложение
     * Вместо пароля используется поле lastName */
    @Query("SELECT * FROM user WHERE firstName = :firstName AND lastName = :lastName")
    suspend fun getUserByFirstNameAndLastName(firstName: String, lastName: String): User?
}