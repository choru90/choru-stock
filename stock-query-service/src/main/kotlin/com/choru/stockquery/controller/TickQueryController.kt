package com.choru.stockquery.controller

import com.choru.stockquery.repository.PriceTickRepository
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class TickQueryController(
    private val repository: PriceTickRepository
) {
    @GetMapping("/ticks/{symbol}")
    fun getTicks(
        @PathVariable symbol: String,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime
    ) = repository.findAllBySymbolAndTradeAtAfterOrderByTradeAtAsc(symbol, from)
}
