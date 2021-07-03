package com.kaya.esports.resolvers

import com.graphql.spring.boot.test.GraphQLResponse
import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.kaya.esports.helper.TestHelper
import com.kaya.esports.repository.TournamentRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TournamentQueryResolverEmptyIT {
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var tournamentRepository: TournamentRepository
    @BeforeAll
    fun setup() {
        tournamentRepository.deleteAll()
    }

    @Test
    fun `whenCalled_Returns_ZeroTournaments`() {
        var testName = "tournamentQueryEmpty"
        var emptyResponse = TestHelper.getGraphQLResponse(testName)
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        JSONAssert.assertEquals(emptyResponse, graphQLResponse.rawResponse.body, true)
    }
}