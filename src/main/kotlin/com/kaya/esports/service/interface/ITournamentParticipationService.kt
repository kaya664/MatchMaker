package com.kaya.esports.service.`interface`

import com.kaya.esports.service.request.TournamentJoiningRequest
import com.kaya.esports.service.response.TournamentJoiningResponse

interface ITournamentParticipationService {
    fun joinTournament(tournamentJoiningRequest: TournamentJoiningRequest): TournamentJoiningResponse
}