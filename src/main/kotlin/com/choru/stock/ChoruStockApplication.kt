package com.choru.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ChoruStockApplication

fun main(args: Array<String>) {
    runApplication<ChoruStockApplication>(*args)
}
