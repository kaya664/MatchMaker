package com.kaya.esports.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "user")
data class User (
        @Id
        var userName: String,
        var name: String,
        var email: String,
        var password: String,
        var country: String,
        var dateOfBirth: Date?)