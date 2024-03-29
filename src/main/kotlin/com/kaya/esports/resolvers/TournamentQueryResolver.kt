package com.kaya.esports.resolvers

import com.kaya.esports.dto.TournamentDTO
import com.kaya.esports.service.`interface`.ITournamentPopulationService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class TournamentQueryResolver(private var tournamentPopulationService: ITournamentPopulationService):
    GraphQLQueryResolver {
    fun tournaments(): List<TournamentDTO?>? {
        return tournamentPopulationService.getTournaments()
    }
}