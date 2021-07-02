package com.kaya.esports.service

import com.kaya.esports.entity.User
import com.kaya.esports.exception.UserAlreadyExistsException
import com.kaya.esports.repository.UserRepository
import com.kaya.esports.service.`interface`.IUserService
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class UserService(private var userRepository: UserRepository): IUserService {
    override fun findUserByUserName(userName: String): User? {
        return userRepository.findUserByUserName(userName);
    }

    override fun createNewUser(user: User): User {
        if(findUserByUserName(user.userName) == null) {
            user.createdDate = Date()
            return userRepository.save(user)
        }
        throw UserAlreadyExistsException("Username exists already", user.userName)
    }
}