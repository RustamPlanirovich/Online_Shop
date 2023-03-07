package com.nauk0a.data.mapper

import com.nauk0a.data.models.User
import com.nauk0a.domain.models.UserDomain


fun User.mapToDomain() = UserDomain(
    id = id, firstName = firstName, lastName = lastName, email = email
)