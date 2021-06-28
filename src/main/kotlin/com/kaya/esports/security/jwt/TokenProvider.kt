package com.kaya.esports.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class TokenProvider {

    private var key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun createToken(userName: String): String {
        return  Jwts.builder()
                .setSubject(userName)
                .setIssuer("MatchMaker")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + 100000))
                .signWith(key)
                .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            if(getUserFromToken(token) != null && !isTokenExpired(token)) {
                return true;
            }
        } catch(e: Exception) {

        }
        return false;
    }

    fun isTokenExpired(token: String): Boolean {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).body.expiration.before(Date())
    }

    fun getUserFromToken(token: String): String {
        var claims: Claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).body
        return claims.subject
    }
}