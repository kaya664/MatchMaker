package com.kaya.esports.dto

import java.math.BigDecimal
import java.util.*

data class TournamentDTO(
    var id: String?,
    var name: String,
    var gameName: String,
    var paymentType: String,
    var paymentCurrency: String,
    var eliminationType: String,
    var status: String,
    var explanation: String
)