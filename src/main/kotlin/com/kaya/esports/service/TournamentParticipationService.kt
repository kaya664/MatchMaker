package com.kaya.esports.service

import com.kaya.esports.entity.TournamentParticipation
import com.kaya.esports.exception.GenericGraphQLException
import com.kaya.esports.repository.TournamentParticipationRepository
import com.kaya.esports.security.AuthenticationHelper
import com.kaya.esports.service.`interface`.ITournamentParticipationService
import com.kaya.esports.service.request.TournamentJoiningRequest
import com.kaya.esports.service.response.TournamentJoiningResponse
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class TournamentParticipationService(private var tournamentParticipationRepository: TournamentParticipationRepository, private var tournamentPopulationService: TournamentPopulationService): ITournamentParticipationService {
    override fun joinTournament(tournamentJoiningRequest: TournamentJoiningRequest): TournamentJoiningResponse {
        var userName: String? = AuthenticationHelper.getCurrentUser()
        if(userName != null) {
            var tournamentDTO = tournamentPopulationService.getTournament(tournamentJoiningRequest.tournamentId)
            if(tournamentDTO != null) {
                if(tournamentParticipationRepository.findByUserNameAndTournamentId(userName, tournamentJoiningRequest.tournamentId) != null) {
                    throw GenericGraphQLException("User already joined to the tournament", userName + " " + tournamentJoiningRequest.tournamentId)
                }
                try {
                    tournamentParticipationRepository.save(TournamentParticipation(UUID.randomUUID().toString(), tournamentJoiningRequest.tournamentId, userName))
                } catch(e: Exception) {
                    throw GenericGraphQLException("Cannot join the tournament", tournamentJoiningRequest.tournamentId)
                }
            }
            return TournamentJoiningResponse(tournamentJoiningRequest.tournamentId, userName)
        }
        throw AuthenticationCredentialsNotFoundException("Authenticated user cannot be found")
    }
}