package com.choru.stockquery.application

import com.choru.stockquery.application.dto.BarDto
import com.choru.stockquery.infrastructure.kafka.TickEvent
import java.time.LocalDateTime

interface ChartService {
    fun getBars(symbol: String, interval: String, from: LocalDateTime, to: LocalDateTime): List<BarDto>
    fun getLatest(symbol: String, interval: String): BarDto?
    fun update(event: TickEvent)
}
