package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.kaya.esports.entity.User
import com.kaya.esports.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class UserQueryResolver(val userRepository: UserRepository) : GraphQLQueryResolver {
    @PreAuthorize("isAuthenticated()")
    fun users(): List<User> {
        val list = userRepository.findAll();
        return list;
    }
}