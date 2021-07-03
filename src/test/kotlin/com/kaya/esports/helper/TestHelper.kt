package com.kaya.esports.helper

import io.micrometer.core.instrument.util.IOUtils
import org.springframework.core.io.ClassPathResource
import java.nio.charset.StandardCharsets

class TestHelper {
    companion object {
        private var GRAPHLQ_QUERY_REQUEST_PATH = "graphql/resolver/query/request/%s.query"
        private var GRAPHLQ_QUERY_RESPONSE_PATH = "graphql/resolver/query/response/%s.json"

        fun getGraphQLRequest(testName: String): String {
            return String.format(GRAPHLQ_QUERY_REQUEST_PATH, testName)
        }

        fun getGraphQLResponse(testName: String): String {
            return IOUtils.toString(ClassPathResource(String.format(GRAPHLQ_QUERY_RESPONSE_PATH, testName)).inputStream, StandardCharsets.UTF_8)
        }
    }
}