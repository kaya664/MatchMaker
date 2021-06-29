package com.kaya.esports.repository

import com.kaya.esports.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>{
    fun findUserByUserName(userName: String?): User?
}