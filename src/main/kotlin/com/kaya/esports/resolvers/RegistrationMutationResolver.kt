package com.kaya.esports.resolvers

import com.kaya.esports.mapper.UserMapper
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.resolvers.response.RegistrationResponse
import com.kaya.esports.service.`interface`.IUserRegistrationService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class RegistrationMutationResolver(private var userRegistrationService: IUserRegistrationService) :
    GraphQLMutationResolver {
    fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        return userRegistrationService.register(registrationRequest)
    }
}