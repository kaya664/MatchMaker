package com.kaya.esports.service.`interface`

import com.kaya.esports.service.request.TournamentCreationRequest
import com.kaya.esports.service.request.TournamentEditRequest
import com.kaya.esports.service.response.TournamentCreationResponse
import com.kaya.esports.service.response.TournamentEditResponse

interface ITournamentManagementService {
    fun createTournament(tournamentCreationRequest: TournamentCreationRequest): TournamentCreationResponse
    fun editTournament(tournamentEditRequest: TournamentEditRequest): TournamentEditResponse
}