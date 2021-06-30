package com.kaya.esports.service.request

import java.math.BigDecimal
import java.util.*

class TournamentEditRequest(
    var tournamentId: String,
    var name: String?,
    var gameName: String?,
    var paymentType: String?,
    var paymentCurrency: String?,
    var eliminationType: String?,
    var price: BigDecimal?,
    var status: String?,
    var date: Date?,
    var explanation: String?
)