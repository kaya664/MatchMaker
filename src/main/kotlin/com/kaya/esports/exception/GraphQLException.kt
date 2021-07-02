package com.kaya.esports.exception

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.lang.RuntimeException

open class GraphQLException(@JvmField override val message: String, private val invalidField: String?) :
    RuntimeException(message),
    GraphQLError {
    override fun getMessage(): String? {
        return super.message
    }

    override fun getLocations(): MutableList<SourceLocation>? {
        return null
    }

    override fun getErrorType(): ErrorType? {
        return null
    }

    override fun getExtensions(): MutableMap<String, Any> {
        var extensions = mutableMapOf<String, Any>()
        if(invalidField != null)
            extensions.put("invalidField", invalidField)
        return extensions
    }
}