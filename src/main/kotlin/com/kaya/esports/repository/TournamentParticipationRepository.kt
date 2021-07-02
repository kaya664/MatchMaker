package com.kaya.esports.repository

import com.kaya.esports.entity.TournamentParticipation
import org.springframework.data.mongodb.repository.MongoRepository

interface TournamentParticipationRepository: MongoRepository<TournamentParticipation, String> {
    fun findByUserNameAndTournamentId(userName: String, tournamentId: String): TournamentParticipation?
}