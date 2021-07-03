package com.kaya.esports.resolvers

import com.graphql.spring.boot.test.GraphQLResponse
import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.kaya.esports.dto.UserDTO
import com.kaya.esports.entity.Tournament
import com.kaya.esports.helper.TestHelper
import com.kaya.esports.repository.TournamentRepository
import com.kaya.esports.resolvers.request.AuthenticationRequest
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.service.UserRegistrationService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TournamentMutationResolverIT {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var userRegistrationService: UserRegistrationService
    @Autowired
    private lateinit var tournamentRepository: TournamentRepository

    private lateinit var validToken: String
    private var expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJheXNtYXlrIiwiaXNzIjoiTWF0Y2hNYWtlciIsImlhdCI6MTYyNTIyMDAxMiwiZXhwIjoxNjI1MjIzNjEyfQ.kYXd4yvs8VF_YGwb5uwANdiKbF9mmykDj3LQyRILzxut-g0_dfEox5pONtwFlmjGjCsSMbDGzlSTJ5Fyo2oUtg"

    @BeforeAll
    fun setup() {
        var userDTO = UserDTO("registeredUser", "registeredUser")
        userRegistrationService.register(RegistrationRequest(userDTO))
        var token = userRegistrationService.authenticate(AuthenticationRequest(UserDTO("registeredUser", "registeredUser"))).token
        if(token != null)
            validToken = token

        var tournament = Tournament("tournamentId", "CS:GO Crazy Boys", "CS:GO", "Crypto", "BTC", "1v1",
            BigDecimal.ONE, "New", Date(), "Fun game", "registeredUser", Date(), null)
        tournamentRepository.save(tournament)

        var anonymousTournament = Tournament("tournamentIdAnonymous", "CS:GO Crazy Boys", "CS:GO", "Crypto", "BTC", "1v1",
            BigDecimal.ONE, "New", Date(), "Fun game", "anonymousUser", Date(), null)
        tournamentRepository.save(anonymousTournament)

        var joinTournament = Tournament("joinTournamentId", "CS:GO Crazy Boys", "CS:GO", "Crypto", "BTC", "1v1",
            BigDecimal.ONE, "New", Date(), "Fun game", "anonymousUser", Date(), null)
        tournamentRepository.save(joinTournament)

        var joinTournamentTwice = Tournament("joinTournamentTwiceId", "CS:GO Crazy Boys", "CS:GO", "Crypto", "BTC", "1v1",
            BigDecimal.ONE, "New", Date(), "Fun game", "anonymousUser", Date(), null)
        tournamentRepository.save(joinTournamentTwice)
    }

    @Test
    fun `whenCalled_CreateTournament_With_NonAuthenticatedUser_Return_AccessIsDenied`() {
        var testName = "tournamentCreationWithNonAuthenticatedUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Exception while fetching data (/createTournament) : Access is denied"))
    }

    @Test
    fun `whenCalled_CreateTournament_With_AuthenticatedUser_With_InvalidToken_Return_TokenIsNotValid`() {
        var testName = "tournamentCreationWithAuthenticatedUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth("invalidToken").postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun `whenCalled_CreateTournament_With_AuthenticatedUser_With_ValidToken_Return_TournamentDTO`() {
        var testName = "tournamentCreationWithAuthenticatedUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.OK))
        assert(graphQLResponse.rawResponse.body!!.contains("{\"data\":{\"createTournament\":{\"createdTournament\":{"))
        assert(graphQLResponse.rawResponse.body!!.contains("\"name\":\"CSGO Turnuvası\",\"gameName\":\"CS:GO\""))
    }

    @Test
    fun `whenCalled_CreateTournament_With_AuthenticatedUser_With_ExpiredToken_Return_TokenIsExpired`() {
        var testName = "tournamentCreationWithAuthenticatedUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(expiredToken).postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @Test
    fun `whenCalled_EditTournament_With_NonAuthenticatedUser_Return_AccessIsDenied`() {
        var testName = "tournamentEditWithNonAuthenticatedUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Exception while fetching data (/editTournament) : Access is denied"))
    }

    @Test
    fun `whenCalled_EditTournament_With_AuthenticatedUser_Return_UpdatedTournamentDTO`() {
        var testName = "tournamentEditWithAuthenticatedUser"
        var response = TestHelper.getGraphQLResponse(testName)
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        JSONAssert.assertEquals(response, graphQLResponse.rawResponse.body, true)
    }

    @Test
    fun `whenCalled_EditTournament_With_AuthenticatedUser_WithWrongTournamentId_Return_TournamentNotFoundException`() {
        var testName = "tournamentEditWithAuthenticatedUserWithWrongId"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Tournament cannot be found"))
    }

    @Test
    fun `whenCalled_EditTournament_With_AuthenticatedUser_WithAnotherUsersTournament_Return_TournamentNotFoundException`() {
        var testName = "tournamentEditWithAuthenticatedUserWithAnotherUsersTournament"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("This user cannot edit this tournament"))
    }

    @Test
    fun `whenCalled_JoinTournament_With_NonAuthenticatedUser_Return_AccessIsDenied`() {
        var testName = "tournamentJoinWithNonAuthenticatedUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Exception while fetching data (/joinTournament) : Access is denied"))
    }

    @Test
    fun `whenCalled_JoinTournament_With_AuthenticatedUser_Return_JoiningInfo`() {
        var testName = "tournamentJoinWithAuthenticatedUser"
        var response = TestHelper.getGraphQLResponse(testName)
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        JSONAssert.assertEquals(response, graphQLResponse.rawResponse.body, true)
    }

    @Test
    fun `whenCalled_JoinTournament_With_AuthenticatedAndAlreadyJoinedUser_Return_UserAlreadyJoinedException`() {
        var testName = "tournamentJoinWithAuthenticatedUserTwice"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        var graphQLResponseSecond: GraphQLResponse =
            graphQLTestTemplate.withBearerAuth(validToken).postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponseSecond.rawResponse.body!!.contains("User already joined to the tournament"))
        assert(graphQLResponseSecond.rawResponse.body!!.contains("registeredUser joinTournamentTwiceId"))
    }
}