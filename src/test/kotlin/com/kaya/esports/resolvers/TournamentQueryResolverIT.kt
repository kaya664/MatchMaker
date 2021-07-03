package com.kaya.esports.resolvers

import com.graphql.spring.boot.test.GraphQLResponse
import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.kaya.esports.dto.TournamentDTO
import com.kaya.esports.helper.TestHelper
import com.kaya.esports.repository.TournamentRepository
import com.kaya.esports.service.TournamentManagementService
import com.kaya.esports.service.request.TournamentCreationRequest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TournamentQueryResolverIT {
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var tournamentManagementService: TournamentManagementService
    @Autowired
    private lateinit var tournamentRepository: TournamentRepository

    @BeforeAll
    fun setup() {
        tournamentRepository.deleteAll()
        var tournamentDTO = TournamentDTO(null, "CS:GO Crazy Boys Flick Shots", "CS:GO", "Crypto", "BTC", "1v1", "New", "Fun game", "System", Date(), null)
        tournamentManagementService.createTournament(TournamentCreationRequest(tournamentDTO))
    }

    @Test
    fun `whenCalled_Returns_AllTournaments`() {
        var testName = "tournamentQueryNotEmpty"
        var response = TestHelper.getGraphQLResponse(testName)
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        JSONAssert.assertEquals(response, graphQLResponse.rawResponse.body, true)
    }
}