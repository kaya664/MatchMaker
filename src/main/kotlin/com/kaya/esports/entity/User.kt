package com.kaya.esports.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User (
        var name: String,
        var userName: String,
        var password: String) {
    @Id
    var id: String = ""
}