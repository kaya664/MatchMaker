package com.kaya.esports.resolvers

import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.response.AuthenticationResponse
import com.kaya.esports.service.UserRegistrationService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class AuthenticationMutationResolver(private var userRegistrationService: UserRegistrationService):
    GraphQLMutationResolver {
    public fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        return userRegistrationService.authenticate(authenticationRequest)
    }
}