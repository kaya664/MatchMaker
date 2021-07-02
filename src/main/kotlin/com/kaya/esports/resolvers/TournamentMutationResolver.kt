package com.kaya.esports.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.kaya.esports.service.TournamentParticipationService
import com.kaya.esports.service.`interface`.ITournamentManagementService
import com.kaya.esports.service.request.TournamentCreationRequest
import com.kaya.esports.service.request.TournamentEditRequest
import com.kaya.esports.service.request.TournamentJoiningRequest
import com.kaya.esports.service.response.TournamentCreationResponse
import com.kaya.esports.service.response.TournamentEditResponse
import com.kaya.esports.service.response.TournamentJoiningResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class TournamentMutationResolver(
    private var tournamentManagementService: ITournamentManagementService,
    private var tournamentParticipationService: TournamentParticipationService
) : GraphQLMutationResolver {
    @PreAuthorize("isAuthenticated")
    fun createTournament(tournamentCreationRequest: TournamentCreationRequest): TournamentCreationResponse {
        return tournamentManagementService.createTournament(tournamentCreationRequest);
    }

    @PreAuthorize("isAuthenticated")
    fun editTournament(tournamentEditRequest: TournamentEditRequest): TournamentEditResponse {
        return tournamentManagementService.editTournament(tournamentEditRequest)
    }

    @PreAuthorize("isAuthenticated")
    fun joinTournament(tournamentJoiningRequest: TournamentJoiningRequest): TournamentJoiningResponse {
        return tournamentParticipationService.joinTournament(tournamentJoiningRequest)
    }
}