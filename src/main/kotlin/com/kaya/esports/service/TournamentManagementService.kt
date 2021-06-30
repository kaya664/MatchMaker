package com.kaya.esports.service

import com.kaya.esports.entity.Tournament
import com.kaya.esports.repository.TournamentRepository
import com.kaya.esports.service.`interface`.ITournamentManagementService
import com.kaya.esports.service.request.TournamentCreationRequest
import com.kaya.esports.service.request.TournamentEditRequest
import com.kaya.esports.service.response.TournamentCreationResponse
import com.kaya.esports.service.response.TournamentEditResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class TournamentManagementService(private var tournamentRepository: TournamentRepository) :
    ITournamentManagementService {
    override fun createTournament(tournamentCreationRequest: TournamentCreationRequest): TournamentCreationResponse {
        if (tournamentCreationRequest == null) {
            return TournamentCreationResponse("Fail")
        }

        tournamentRepository.save(
            Tournament(
                UUID.randomUUID().toString(),
                tournamentCreationRequest.name,
                tournamentCreationRequest.gameName,
                tournamentCreationRequest.paymentType,
                tournamentCreationRequest.paymentCurrency,
                tournamentCreationRequest.eliminationType,
                null,
                tournamentCreationRequest.status,
                null,
                tournamentCreationRequest.explanation
            )
        )

        return TournamentCreationResponse("Success")
    }

    override fun editTournament(tournamentEditRequest: TournamentEditRequest): TournamentEditResponse {
        if(tournamentEditRequest == null)
            return TournamentEditResponse("Fail")
        var tournamentOptional: Optional<Tournament> = tournamentRepository.findById(tournamentEditRequest.tournamentId)
        if(tournamentOptional == null || tournamentOptional.isEmpty)
            return TournamentEditResponse("Fail")

        var tournament = tournamentOptional.get()

        if(tournamentEditRequest.name != null)
            tournament.name = tournamentEditRequest.name!!

        if(tournamentEditRequest.gameName != null)
            tournament.gameName = tournamentEditRequest.gameName!!

        tournamentRepository.save(tournament)

        return TournamentEditResponse("Success")
    }
}