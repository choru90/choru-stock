package com.choru.tickcollector.adapter.`in`

import com.choru.tickcollector.application.port.`in`.IngestPriceTickUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PriceSyncScheduler(
    private val ingest: IngestPriceTickUseCase
) {
    @Scheduled(fixedDelayString = "\${app.price-sync.interval-ms:500}")
    fun sync() = ingest.ingest()
}