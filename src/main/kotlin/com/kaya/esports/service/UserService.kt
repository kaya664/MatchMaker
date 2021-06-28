package com.kaya.esports.service

import com.kaya.esports.entity.User
import com.kaya.esports.repository.UserRepository
import com.kaya.esports.service.`interface`.IUserService
import org.springframework.stereotype.Service

@Service
class UserService(private var userRepository: UserRepository): IUserService {
    override fun findUserByUserName(userName: String): User {
        return userRepository.findUserByUserName(userName);
    }
}