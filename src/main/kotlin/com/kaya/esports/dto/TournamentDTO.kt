package com.kaya.esports.dto

data class TournamentDTO(var id: String,
                    var name: String,
                    var gameName: String,
                    var paymentType: String,
                    var paymentCurrency: String,
                    var eliminationType: String,
                    var status: String,
                    var explanation: String) {
}