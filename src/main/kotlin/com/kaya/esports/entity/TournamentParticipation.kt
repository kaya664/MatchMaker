package com.kaya.esports.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "tournament_participation")
class TournamentParticipation(
    @Id
    var id: String,
    var tournamentId: String,
    var userName: String
)