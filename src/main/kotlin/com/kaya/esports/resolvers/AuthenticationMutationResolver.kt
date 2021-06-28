package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.response.AuthenticationResponse
import org.springframework.stereotype.Component

@Component
class AuthenticationMutationResolver: GraphQLMutationResolver {
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        return AuthenticationResponse("Token")
    }
}