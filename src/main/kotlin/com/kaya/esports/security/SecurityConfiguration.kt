package com.kaya.esports.security

import com.kaya.esports.security.jwt.JWTFilter
import graphql.GraphQL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(private var jwtFilter: JWTFilter, private var userDetailsService: DomainUserDetailsService) : WebSecurityConfigurerAdapter(){

    @Autowired
    fun passwordEncoderConfigurer(builder: AuthenticationManagerBuilder) {
        builder.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder())
    }

    @Bean
    fun getPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun getAuthenticationManager() : AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()?.authorizeRequests()?.anyRequest()?.permitAll()?.and()
            ?.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}