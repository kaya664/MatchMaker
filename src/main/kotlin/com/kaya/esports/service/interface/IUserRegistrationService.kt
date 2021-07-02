package com.kaya.esports.service.`interface`

import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.resolvers.response.AuthenticationResponse
import com.kaya.esports.resolvers.response.RegistrationResponse

interface IUserRegistrationService {
    fun register(registrationRequest: RegistrationRequest): RegistrationResponse
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse
}
