package com.kaya.esports.exception

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.lang.RuntimeException

class UserAlreadyExistsException(@JvmField override val message: String, private val invalidField: String): GraphQLException(message, invalidField) {
    override fun getMessage(): String? {
        return super.getMessage()
    }
}