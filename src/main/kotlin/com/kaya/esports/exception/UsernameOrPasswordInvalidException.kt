package com.kaya.esports.exception

class UsernameOrPasswordInvalidException(@JvmField override val message: String, private val invalidField: String): GraphQLException(message, invalidField){
    override fun getMessage(): String? {
        return super.getMessage()
    }
}