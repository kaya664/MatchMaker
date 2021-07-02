package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.response.AuthenticationResponse
import com.kaya.esports.security.jwt.TokenProvider
import com.kaya.esports.service.UserRegistrationService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import kotlin.Exception

@Component
class AuthenticationMutationResolver(private var userRegistrationService: UserRegistrationService):
    GraphQLMutationResolver {
    public fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        return userRegistrationService.authenticate(authenticationRequest)
    }
}