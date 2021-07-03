package com.kaya.esports.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document(collection = "tournament")
data class Tournament(
    @Id
    var id: String?,
    var name: String,
    var gameName: String,
    var paymentType: String,
    var paymentCurrency: String,
    var eliminationType: String,
    var price: BigDecimal,
    var status: String,
    var date: Date,
    var explanation: String,
    var createdBy: String?,
    var createdDate: Date?,
    var updatedDate: Date?
)