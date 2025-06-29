package com.choru.stockquery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockQueryServiceApplication

fun main(args: Array<String>) {
    runApplication<StockQueryServiceApplication>(*args)
}
