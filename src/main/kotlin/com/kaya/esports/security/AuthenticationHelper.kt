package com.kaya.esports.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class AuthenticationHelper {
    companion object {
        fun getCurrentUser(): String? {
            var userName: String? = null
            if(SecurityContextHolder.getContext().authentication != null) {
                var principal = SecurityContextHolder.getContext().authentication
                if(principal is UserDetails) {
                    userName = principal.username
                } else if (principal is UsernamePasswordAuthenticationToken){
                    userName = principal.principal.toString()
                }
            }
            return userName
        }
    }
}