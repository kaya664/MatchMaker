package com.kaya.esports.service.`interface`

import com.kaya.esports.entity.User

interface IUserService {
    fun findUserByUserName(userName: String): User
}