package com.kaya.esports.service.`interface`

import com.kaya.esports.service.request.TournamentJoiningRequest

interface ITournamentParticipationService {
    fun joinTournament(tournamentJoiningRequest: TournamentJoiningRequest)
}