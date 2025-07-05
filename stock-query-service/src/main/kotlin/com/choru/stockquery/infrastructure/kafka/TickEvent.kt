package com.choru.stockquery.infrastructure.kafka

import java.math.BigDecimal
import java.time.LocalDateTime

data class TickEvent(
    val symbol: String,
    val price: BigDecimal,
    val volume: Long,
    val timestamp: LocalDateTime
)
