package com.choru.tickcollector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
class TickCollectorServiceApplication

fun main(args: Array<String>) {
    runApplication<TickCollectorServiceApplication>(*args)
}

