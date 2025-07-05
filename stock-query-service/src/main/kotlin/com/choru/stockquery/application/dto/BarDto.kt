package com.choru.stockquery.application.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class BarDto(
    val symbol: String,
    val interval: String,
    var time: LocalDateTime,
    var open: BigDecimal,
    var high: BigDecimal,
    var low: BigDecimal,
    var close: BigDecimal,
    var volume: Long
)
