package com.choru.stockquery.adapter.`in`.controller

import com.choru.stockquery.application.ChartService
import com.choru.stockquery.application.dto.BarDto
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/chart")
class ChartController(
    private val service: ChartService
) {

    @GetMapping("/{symbol}")
    fun getBars(
        @PathVariable symbol: String,
        @RequestParam interval: String,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: LocalDateTime
    ): List<BarDto> = service.getBars(symbol, interval, from, to)

    @GetMapping("/{symbol}/latest")
    fun latest(
        @PathVariable symbol: String,
        @RequestParam interval: String
    ): BarDto? = service.getLatest(symbol, interval)
}
