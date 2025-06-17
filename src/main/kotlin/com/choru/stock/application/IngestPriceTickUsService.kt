package com.choru.stock.application

import com.choru.stock.application.port.`in`.IngestPriceTickUseCase
import com.choru.stock.application.port.out.ExchangeClient
import org.springframework.stereotype.Service
import kotlin.math.exp

@Service
class IngestPriceTickUsService(
    private val exchangeClient: ExchangeClient
) : IngestPriceTickUseCase {

    override fun ingest() {
        val tick =
    }
}