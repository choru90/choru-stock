package com.choru.stockquery.application

import com.choru.stockquery.adapter.out.TickSummaryRepository
import com.choru.stockquery.application.dto.BarDto
import com.choru.stockquery.domain.TickSummary
import com.choru.stockquery.infrastructure.ws.ChartWebSocketHandler
import com.choru.stockquery.infrastructure.kafka.TickEvent
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

@Service
class ChartServiceImpl(
    private val repository: TickSummaryRepository,
    private val webSocketHandler: ChartWebSocketHandler
) : ChartService {

    private val cache = ConcurrentHashMap<Pair<String, String>, BarDto>()

    override fun getBars(symbol: String, interval: String, from: LocalDateTime, to: LocalDateTime): List<BarDto> {
        return repository.findBars(symbol, interval, from, to).map { it.toDto() }
    }

    override fun getLatest(symbol: String, interval: String): BarDto? = cache[symbol to interval]

    override fun update(event: TickEvent) {
        listOf(
            "1m" to ChronoUnit.MINUTES,
            "1h" to ChronoUnit.HOURS,
            "1d" to ChronoUnit.DAYS
        ).forEach { (interval, unit) ->
            val time = event.timestamp.truncatedTo(unit)
            val key = event.symbol to interval
            val bar = cache.compute(key) { _, existing ->
                if (existing == null || existing.time != time) {
                    BarDto(event.symbol, interval, time, event.price, event.price, event.price, event.price, event.volume)
                } else {
                    existing.high = existing.high.max(event.price)
                    existing.low = existing.low.min(event.price)
                    existing.close = event.price
                    existing.volume += event.volume
                    existing
                }
            }!!
            webSocketHandler.broadcast(bar)
        }
    }

    private fun TickSummary.toDto() = BarDto(
        symbol = id.symbol,
        interval = id.interval,
        time = id.time,
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume
    )
}
