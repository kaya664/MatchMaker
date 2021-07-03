package com.kaya.esports.mapper

import com.kaya.esports.dto.TournamentDTO
import com.kaya.esports.entity.Tournament
import java.math.BigDecimal
import java.util.*


class TournamentMapper {
    companion object {
        fun tournamentEntityToTournamentDTO(tournament: Tournament): TournamentDTO {
            return TournamentDTO(
                tournament.id,
                tournament.name,
                tournament.gameName,
                tournament.paymentType,
                tournament.paymentCurrency,
                tournament.eliminationType,
                tournament.status,
                tournament.explanation,
                tournament.createdBy,
                tournament.createdDate,
                tournament.updatedDate
            )
        }

        fun tournamentEntityListToTournamentDTOList(tournamentEntityList: MutableList<Tournament?>?): MutableList<TournamentDTO?>? {
            if (tournamentEntityList == null)
                return null

            var tournamentDTOList: MutableList<TournamentDTO?> = mutableListOf()
            for (tournamentEntity in tournamentEntityList) {
                if (tournamentEntity != null)
                    tournamentDTOList.add(tournamentEntityToTournamentDTO(tournamentEntity))
            }
            return tournamentDTOList
        }

        fun tournamentDTOToTournamentEntity(tournamentDTO: TournamentDTO): Tournament {
            return Tournament(
                tournamentDTO.id,
                tournamentDTO.name,
                tournamentDTO.gameName,
                tournamentDTO.paymentType,
                tournamentDTO.paymentCurrency,
                tournamentDTO.eliminationType,
                BigDecimal.ONE,
                tournamentDTO.status,
                Date(),
                tournamentDTO.explanation,
                tournamentDTO.createdBy,
                tournamentDTO.createdDate,
                tournamentDTO.updatedDate
            )
        }
    }
}