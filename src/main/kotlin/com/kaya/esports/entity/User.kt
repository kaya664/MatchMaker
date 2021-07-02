package com.kaya.esports.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "user")
data class User (
        @Id
        var userName: String,
        var password: String,
        @CreatedDate
        var createdDate: Date?,
        @LastModifiedDate
        var updatedDate: Date?)