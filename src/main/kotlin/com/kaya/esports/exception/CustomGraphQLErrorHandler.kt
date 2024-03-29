package com.kaya.esports.exception

import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.kickstart.execution.error.GraphQLErrorHandler
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class CustomGraphQLErrorHandler: GraphQLErrorHandler {
    override fun processErrors(list: List<GraphQLError?>): List<GraphQLError?>? {
        return list.stream().map { error: GraphQLError? ->
            getNested(
                error
            )
        }.collect(Collectors.toList())
    }

    private fun getNested(error: GraphQLError?): GraphQLError? {
        if (error is ExceptionWhileDataFetching) {
            val exceptionError = error
            if (exceptionError.exception is GraphQLError) {
                return exceptionError.exception as GraphQLError
            }
        }
        return error
    }
}