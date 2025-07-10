package com.choru.stockquery.application

import com.choru.stockquery.adapter.out.TickSummaryRepository
import com.choru.stockquery.application.dto.BarDto
import com.choru.stockquery.domain.TickSummary
import com.choru.stockquery.domain.TickSummaryId
import com.choru.stockquery.infrastructure.ws.ChartWebSocketHandler
import com.choru.stockquery.infrastructure.kafka.TickEvent
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters
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
        val intervals = listOf<Pair<String, (LocalDateTime) -> LocalDateTime>>(
            "1m" to { it.truncatedTo(ChronoUnit.MINUTES) },
            "1h" to { it.truncatedTo(ChronoUnit.HOURS) },
            "1d" to { it.truncatedTo(ChronoUnit.DAYS) },
            "1w" to { it.truncatedTo(ChronoUnit.DAYS).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) },
            "1M" to { it.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS) }
        )

        intervals.forEach { (interval, adjuster) ->
            val time = adjuster(event.timestamp)
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

            if (interval in setOf("1d", "1w", "1M")) {
                repository.save(
                    TickSummary(
                        TickSummaryId(event.symbol, interval, time),
                        bar.open,
                        bar.high,
                        bar.low,
                        bar.close,
                        bar.volume
                    )
                )
            }

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
