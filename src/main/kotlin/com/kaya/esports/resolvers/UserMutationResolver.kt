package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.entity.User
import com.kaya.esports.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMutationResolver(val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder) : GraphQLMutationResolver {

    fun newUser(name: String, userName: String, password: String): User {
        val user = User(name, userName, passwordEncoder.encode(password))
        user.id = UUID.randomUUID().toString()
        userRepository.save(user)
        return user;
    }
}