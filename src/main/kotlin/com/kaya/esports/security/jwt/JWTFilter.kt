package com.kaya.esports.security.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTFilter(private var tokenProvider: TokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        var jwt: String? = null
        var userName: String? = null

        if(request.getHeader("Authorization") != null) {
            val bearerString: String = request.getHeader("Authorization")
            if(!bearerString.isNullOrEmpty() && bearerString.startsWith("Bearer ")) {
                jwt = bearerString.substring(7)
                userName = tokenProvider.getUserFromToken(jwt)
            }
        }

        if(!userName.isNullOrEmpty() && !jwt.isNullOrEmpty() && SecurityContextHolder.getContext().authentication == null) {
            if(tokenProvider.validateToken(jwt)) {
                var upat = UsernamePasswordAuthenticationToken(userName, null, listOf())
                upat.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = upat
            }
        }

        filterChain.doFilter(request, response)
    }
}