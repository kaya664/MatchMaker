package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.entity.User
import com.kaya.esports.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMutationResolver(val userRepository: UserRepository) : GraphQLMutationResolver {
    fun newUser(name: String, userName: String, password: String): User {
        val user = User(name, userName, password)
        user.id = UUID.randomUUID().toString()
        userRepository.save(user)
        return user;
    }
}