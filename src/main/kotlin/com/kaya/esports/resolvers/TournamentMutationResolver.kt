package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.service.`interface`.ITournamentManagementService
import com.kaya.esports.service.request.TournamentCreationRequest
import com.kaya.esports.service.request.TournamentEditRequest
import com.kaya.esports.service.response.TournamentCreationResponse
import com.kaya.esports.service.response.TournamentEditResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class TournamentMutationResolver(private var tournamentManagementService: ITournamentManagementService): GraphQLMutationResolver{
    @PreAuthorize("isAuthenticated")
    fun createTournament(tournamentCreationRequest: TournamentCreationRequest): TournamentCreationResponse {
        if(tournamentCreationRequest != null) {
            tournamentManagementService.createTournament(tournamentCreationRequest);
        }
        return TournamentCreationResponse("Success");
    }

    @PreAuthorize("isAuthenticated")
    fun editTournament(tournamentEditRequest: TournamentEditRequest): TournamentEditResponse {
        if(tournamentEditRequest != null) {
            tournamentManagementService.editTournament(tournamentEditRequest)
        }
        return TournamentEditResponse("Success")
    }
}