package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.kaya.esports.entity.User
import com.kaya.esports.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserQueryResolver(val userRepository: UserRepository) : GraphQLQueryResolver {
    fun users(): List<User> {
        val list = userRepository.findAll();
        return list;
    }
}