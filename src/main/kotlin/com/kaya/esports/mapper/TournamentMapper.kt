package com.kaya.esports.mapper

import com.kaya.esports.dto.TournamentDTO
import com.kaya.esports.entity.Tournament

class TournamentMapper {
    companion object {
        fun mapTournamentEntityToDTO(tournament: Tournament): TournamentDTO? {
            if(tournament == null)
                return null;

            return TournamentDTO(tournament.id, tournament.name, tournament.gameName, tournament.paymentType, tournament.paymentCurrency, tournament.eliminationType, tournament.status, tournament.explanation)
        }

        fun mapTournamentEntityListToDTOList(tournamentList: List<Tournament>): List<TournamentDTO?>? {
            if(tournamentList == null)
                return null;

            var tournamentDTOList: MutableList<TournamentDTO?> = mutableListOf()
            for (tournament in tournamentList) {
                tournamentDTOList.add(mapTournamentEntityToDTO(tournament))
            }
            return tournamentDTOList
        }
    }
}