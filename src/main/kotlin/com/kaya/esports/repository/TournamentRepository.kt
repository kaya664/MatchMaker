package com.kaya.esports.repository

import com.kaya.esports.entity.Tournament
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentRepository: MongoRepository<Tournament, String> {
}