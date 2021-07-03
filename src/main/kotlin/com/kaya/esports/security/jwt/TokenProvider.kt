package com.kaya.esports.security.jwt

import com.kaya.esports.exception.GenericGraphQLException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.security.Key
import java.util.*

@Component
class TokenProvider {
    var jwtSecret: String = "fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8"
    var jwtExpiration: String = "3600000"
    private var key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

    fun createToken(userName: String): String {
        return  Jwts.builder()
                .setSubject(userName)
                .setIssuer("MatchMaker")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + jwtExpiration.toInt()))
                .signWith(key)
                .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            if(getUserFromToken(token) == null) {
                throw GenericGraphQLException("Token is not valid", token)
            } else if(isTokenExpired(token)){
                throw GenericGraphQLException("Token is expired", token)
            }
            return true
        } catch(e: Exception) {
            throw GenericGraphQLException(e.localizedMessage, null)
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        return getClaimsFromToken(token).expiration.before(Date())
    }

    fun getUserFromToken(token: String): String {
        return getClaimsFromToken(token).subject
    }

    private fun getClaimsFromToken(token: String): Claims {
        var claims: Claims
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).body
        } catch(e: ExpiredJwtException) {
            throw GenericGraphQLException("Token is expired", token)
        } catch(e: UnsupportedJwtException) {
            throw GenericGraphQLException("Token is built faulty", token)
        } catch(e: java.lang.Exception) {
            throw GenericGraphQLException("Token is not valid", token)
        }
        return claims
    }
}