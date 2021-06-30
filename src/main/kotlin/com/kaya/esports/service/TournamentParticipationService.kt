package com.kaya.esports.service

import com.kaya.esports.service.`interface`.ITournamentParticipationService
import com.kaya.esports.service.request.TournamentJoiningRequest
import org.springframework.stereotype.Service

@Service
class TournamentParticipationService: ITournamentParticipationService {
    override fun joinTournament(tournamentJoiningRequest: TournamentJoiningRequest) {

    }
}