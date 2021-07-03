package com.kaya.esports.resolvers

import com.graphql.spring.boot.test.GraphQLResponse
import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.kaya.esports.helper.TestHelper
import com.kaya.esports.dto.UserDTO
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.service.UserRegistrationService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationMutationResolverIT() {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Autowired
    private lateinit var userRegistrationService: UserRegistrationService

    @BeforeAll
    fun setup() {
        var userDTO = UserDTO("authenticationTestUser", "authenticationTestUser")
        userRegistrationService.register(RegistrationRequest(userDTO))
    }

    @Test
    fun `whenCalledWithRegisteredUserReturnToken`() {
        var testName = "authenticationWithRegisteredUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.OK))
        assert(graphQLResponse.rawResponse.body!!.contains("token"))
    }

    @Test
    fun `whenCalledWithNonregisteredUserReturnUserNotFoundException`() {
        var testName = "authenticationWithNonregisteredUser"
        var graphQLResponse: GraphQLResponse =
                graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.OK))
        assert(graphQLResponse.rawResponse.body!!.contains("Username or password invalid"))
    }

    @Test
    fun `whenCalledWithoutUsernameReturnFieldValidationError`() {
        var testName = "authenticationWithUsernameEmptyRequest"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Validation error of type WrongType"))
        assert(graphQLResponse.rawResponse.body!!.contains("is missing required fields '[userName]'"))
    }

    @Test
    fun `whenCalledWithoutPasswordReturnFieldCannotBeNullException`() {
        var testName = "authenticationWithPasswordEmptyRequest"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.withClearHeaders().postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Validation error of type WrongType"))
        assert(graphQLResponse.rawResponse.body!!.contains("is missing required fields '[password]'"))
    }
}