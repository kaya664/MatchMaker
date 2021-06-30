package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.response.AuthenticationResponse
import com.kaya.esports.security.jwt.TokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import kotlin.Exception

@Component
class AuthenticationMutationResolver(private var tokenProvider: TokenProvider, private var authenticationManager: AuthenticationManager):
    GraphQLMutationResolver {
    public fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        try {
            var upat = UsernamePasswordAuthenticationToken(authenticationRequest.userName, authenticationRequest.password)
            authenticationManager.authenticate(upat)
            return AuthenticationResponse(tokenProvider.createToken("userName"))
        } catch (e: BadCredentialsException) {
            return AuthenticationResponse("Username or password is wrong")
        } catch(e: Exception) {
            throw e
        }
    }
}