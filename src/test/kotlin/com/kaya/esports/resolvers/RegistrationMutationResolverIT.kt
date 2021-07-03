package com.kaya.esports.resolvers

import com.graphql.spring.boot.test.GraphQLResponse
import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.kaya.esports.dto.UserDTO
import com.kaya.esports.helper.TestHelper
import com.kaya.esports.resolvers.request.RegistrationRequest
import com.kaya.esports.service.UserRegistrationService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationMutationResolverIT {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var userRegistrationService: UserRegistrationService

    @BeforeAll
    fun setup() {
        var userDTO = UserDTO("alreadyRegisteredUser", "alreadyRegisteredUser")
        userRegistrationService.register(RegistrationRequest(userDTO))
    }

    @Test
    fun `whenCalledWithNonregisteredUserReturnUserDTO`() {
        var testName = "registrationWithNonregisteredUser"
        var response = TestHelper.getGraphQLResponse(testName)
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.OK))
        JSONAssert.assertEquals(response, graphQLResponse.rawResponse.body, true)
    }

    @Test
    fun `whenCalledWithAlreadyRegisteredUserReturnUserAlreadyExistsException`() {
        var testName = "registrationWithAlreadyRegisteredUser"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.statusCode.equals(HttpStatus.OK))
        assert(graphQLResponse.rawResponse.body!!.contains("Username exists already"))
    }

    @Test
    fun `whenCalledWithoutUsernameReturnFieldValidationError`() {
        var testName = "registrationWithUsernameEmptyRequest"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Validation error of type WrongType"))
        assert(graphQLResponse.rawResponse.body!!.contains("is missing required fields '[userName]'"))
    }

    @Test
    fun `whenCalledWithoutPasswordReturnFieldCannotBeNullException`() {
        var testName = "registrationWithPasswordEmptyRequest"
        var graphQLResponse: GraphQLResponse =
            graphQLTestTemplate.postForResource(TestHelper.getGraphQLRequest(testName))
        assert(graphQLResponse.rawResponse.body!!.contains("Validation error of type WrongType"))
        assert(graphQLResponse.rawResponse.body!!.contains("is missing required fields '[password]'"))
    }
}