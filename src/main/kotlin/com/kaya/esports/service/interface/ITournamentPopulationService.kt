package com.kaya.esports.service.`interface`

import com.kaya.esports.dto.TournamentDTO

interface ITournamentPopulationService {
    fun getTournaments(): List<TournamentDTO?>?
}
