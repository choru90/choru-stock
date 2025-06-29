package com.choru.tickcollector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
@EnableJpaAuditing
@SpringBootApplication
class TickCollectorServiceApplication

fun main(args: Array<String>) {
    runApplication<TickCollectorServiceApplication>(*args)
}

