package com.kaya.esports.resolvers.request

data class RegistrationRequest(var userName: String,
                               var name: String,
                               var email: String,
                               var password: String,
                               var country: String)