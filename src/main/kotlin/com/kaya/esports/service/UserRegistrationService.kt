package com.kaya.esports.service

import com.kaya.esports.exception.GenericGraphQLException
import com.kaya.esports.exception.UserAlreadyExistsException
import com.kaya.esports.exception.UsernameOrPasswordInvalidException
import com.kaya.esports.mapper.UserMapper
import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.resolvers.response.AuthenticationResponse
import com.kaya.esports.resolvers.response.RegistrationResponse
import com.kaya.esports.security.jwt.TokenProvider
import com.kaya.esports.service.`interface`.IUserRegistrationService
import com.kaya.esports.service.`interface`.IUserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserRegistrationService(
    private var userService: IUserService,
    private var passwordEncoder: BCryptPasswordEncoder,
    private var tokenProvider: TokenProvider,
    private var authenticationManager: AuthenticationManager
) : IUserRegistrationService {
    override fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        registrationRequest.user.password = passwordEncoder.encode(registrationRequest.user.password)
        var user = userService.createNewUser(UserMapper.userDTOToUserEntity(registrationRequest.user))
        return RegistrationResponse(UserMapper.userEntityToUserDTO(user))
    }

    override fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        try {
            var upat =
                UsernamePasswordAuthenticationToken(authenticationRequest.user.userName, authenticationRequest.user.password)
            authenticationManager.authenticate(upat)
            return AuthenticationResponse(tokenProvider.createToken(authenticationRequest.user.userName))
        } catch (e: BadCredentialsException) {
            throw UsernameOrPasswordInvalidException("Username or password invalid", authenticationRequest.user.userName)
        } catch (e: Exception) {
            throw GenericGraphQLException(e.localizedMessage, null)
        }
    }
}