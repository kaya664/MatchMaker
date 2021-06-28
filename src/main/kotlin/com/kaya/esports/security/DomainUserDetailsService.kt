package com.kaya.esports.security

import com.kaya.esports.entity.User
import com.kaya.esports.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class DomainUserDetailsService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(userName: String?): UserDetails {
        print(userName);
        if(userName != null) {
            val user = userRepository.findUserByUserName(userName)
            return createSpringSecurityUser(userName, user)
        }
        throw Exception();
    }

    private fun createSpringSecurityUser(userName: String?, user: User): org.springframework.security.core.userdetails.User {
        return org.springframework.security.core.userdetails.User(userName, user.password, listOf());
    }
}