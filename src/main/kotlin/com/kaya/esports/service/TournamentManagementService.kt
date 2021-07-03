package com.kaya.esports.service

import com.kaya.esports.entity.Tournament
import com.kaya.esports.exception.GenericGraphQLException
import com.kaya.esports.mapper.TournamentMapper
import com.kaya.esports.repository.TournamentRepository
import com.kaya.esports.security.AuthenticationHelper
import com.kaya.esports.service.`interface`.ITournamentManagementService
import com.kaya.esports.service.request.TournamentCreationRequest
import com.kaya.esports.service.request.TournamentEditRequest
import com.kaya.esports.service.response.TournamentCreationResponse
import com.kaya.esports.service.response.TournamentEditResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TournamentManagementService(private var tournamentPopulationService: TournamentPopulationService, private var tournamentRepository: TournamentRepository) :
    ITournamentManagementService {

    @Transactional
    override fun createTournament(tournamentCreationRequest: TournamentCreationRequest): TournamentCreationResponse {
        var tournamentEntity = TournamentMapper.tournamentDTOToTournamentEntity(tournamentCreationRequest.tournament)
        tournamentEntity.id = UUID.randomUUID().toString()
        tournamentEntity.createdBy = AuthenticationHelper.getCurrentUser()
        tournamentEntity.createdDate = Date()
        var createdTournament = TournamentMapper.tournamentEntityToTournamentDTO(tournamentRepository.save(tournamentEntity))
        return TournamentCreationResponse(createdTournament)
    }

    @Transactional
    override fun editTournament(tournamentEditRequest: TournamentEditRequest): TournamentEditResponse {
        var tournamentDTO = tournamentPopulationService.getTournament(tournamentEditRequest.tournament.id)

        if(!tournamentDTO.createdBy.equals(AuthenticationHelper.getCurrentUser())) {
            throw GenericGraphQLException("This user cannot edit this tournament", AuthenticationHelper.getCurrentUser())
        }

        var tournamentEditDTO = tournamentEditRequest.tournament
        tournamentDTO.name = tournamentEditDTO.name
        tournamentDTO.gameName = tournamentEditDTO.gameName
        tournamentDTO.paymentType = tournamentEditDTO.paymentType
        tournamentDTO.paymentCurrency = tournamentEditDTO.paymentCurrency
        tournamentDTO.eliminationType = tournamentEditDTO.eliminationType
        tournamentDTO.status = tournamentEditDTO.status
        tournamentDTO.explanation = tournamentEditDTO.explanation
        tournamentDTO.updatedDate = Date()

        var tournamentEntity: Tournament = TournamentMapper.tournamentDTOToTournamentEntity(tournamentDTO)
        var editedTournament = tournamentRepository.save(tournamentEntity)
        return TournamentEditResponse(TournamentMapper.tournamentEntityToTournamentDTO(editedTournament))
    }
}