package com.kaya.esports.service

import com.kaya.esports.dto.TournamentDTO
import com.kaya.esports.mapper.TournamentMapper
import com.kaya.esports.repository.TournamentRepository
import com.kaya.esports.service.`interface`.ITournamentPopulationService
import org.springframework.stereotype.Service

@Service
class TournamentPopulationService(private var tournamentRepository: TournamentRepository): ITournamentPopulationService {
    override fun getTournaments(): List<TournamentDTO?>? {
        var tournamentList = tournamentRepository.findAll()
        return TournamentMapper.mapTournamentEntityListToDTOList(tournamentList);
    }
}