package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.entity.User
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.resolvers.response.RegistrationResponse
import com.kaya.esports.service.`interface`.IUserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class RegistrationMutationResolver(private var userService: IUserService, private var passwordEncoder: BCryptPasswordEncoder) :
    GraphQLMutationResolver {
    fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        if(registrationRequest != null) {
            var user = userService.createNewUser(User(registrationRequest.userName, registrationRequest.name, registrationRequest.email, passwordEncoder.encode(registrationRequest.password), registrationRequest.country, null))
            if(user != null) {
                return RegistrationResponse("Success")
            } else {
                return RegistrationResponse("Fail, username exists")
            }
        }
        return RegistrationResponse("Fail, request must not be null")
    }
}