package com.kaya.esports

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MatchMakerApplication

fun main(args: Array<String>) {
	runApplication<MatchMakerApplication>(*args)
}
