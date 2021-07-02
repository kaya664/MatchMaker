package com.kaya.esports.security

import com.kaya.esports.entity.User
import com.kaya.esports.service.`interface`.IUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class DomainUserDetailsService(private var userService: IUserService) : UserDetailsService {
    override fun loadUserByUsername(userName: String?): UserDetails {
        var user: User? = null

        if (userName != null) {
            user = userService.findUserByUserName(userName)
        }

        if(user != null) {
            return org.springframework.security.core.userdetails.User(user.userName, user.password, listOf())
        }
        throw UsernameNotFoundException(userName)
    }
}