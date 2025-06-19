package com.choru.stock.application

import com.choru.stock.application.port.`in`.IngestPriceTickUseCase
import com.choru.stock.application.port.out.ExchangeClient
import com.choru.stock.application.port.out.PriceTickRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class IngestPriceTickService(
    private val exchangeClient: ExchangeClient,
    private val priceTickRepository: PriceTickRepository
) : IngestPriceTickUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun ingest() {
        val ticks = exchangeClient.fetchLatestTicks()
        if (ticks.isEmpty()) {
            logger.info(">>> 모의 거래소에 새로운 틱이 없습니다.")
        } else {
            logger.info(">>> ${ticks.size}개 틱을 가져왔습니다:")
            ticks.forEach { tick ->
                logger.info(" • {} @ {} → {} (vol={})",
                    tick.symbol.value,
                    tick.tradedAt,
                    tick.price.value,
                    tick.volume
                )
            }
            priceTickRepository.saveAll(ticks)
        }
    }
}