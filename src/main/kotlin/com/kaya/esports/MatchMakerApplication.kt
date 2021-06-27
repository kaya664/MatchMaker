package com.kaya.esports

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MatchMakerApplication

fun main(args: Array<String>) {
	runApplication<MatchMakerApplication>(*args)
}
