package com.kaya.esports.service

import com.kaya.esports.dto.TournamentDTO
import com.kaya.esports.entity.Tournament
import com.kaya.esports.exception.GenericGraphQLException
import com.kaya.esports.mapper.TournamentMapper
import com.kaya.esports.repository.TournamentRepository
import com.kaya.esports.service.`interface`.ITournamentPopulationService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class TournamentPopulationService(private var tournamentRepository: TournamentRepository): ITournamentPopulationService {
    override fun getTournaments(): List<TournamentDTO?>? {
        var tournamentList = tournamentRepository.findAll()
        return TournamentMapper.tournamentEntityListToTournamentDTOList(tournamentList);
    }

    override fun getTournament(id: String?): TournamentDTO {
        if(id.isNullOrEmpty())
            throw GenericGraphQLException("Tournament cannot be found", id)
        var tournamentOptional = tournamentRepository.findById(id)
        if(tournamentOptional.isEmpty)
            throw GenericGraphQLException("Tournament cannot be found", id)
        return TournamentMapper.tournamentEntityToTournamentDTO(tournamentOptional.get())
    }
}