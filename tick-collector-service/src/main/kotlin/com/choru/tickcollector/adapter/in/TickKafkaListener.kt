package com.choru.tickcollector.adapter.`in`

import com.choru.tickcollector.adapter.out.dto.ExchangeTickDto
import com.choru.tickcollector.application.port.out.PriceTickRepository
import com.choru.tickcollector.domain.Money
import com.choru.tickcollector.domain.PriceTick
import com.choru.tickcollector.domain.Symbol
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class TickKafkaListener(
    private val mapper: ObjectMapper,
    private val repository: PriceTickRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topic:ticks}"], groupId = "tick-collector")
    fun listen(message: String) {
        try {
            val dto = mapper.readValue(message, ExchangeTickDto::class.java)
            val tick = PriceTick(
                symbol = Symbol(dto.symbol),
                tradedAt = dto.timestamp,
                price = Money(BigDecimal(dto.price)),
                volume = dto.volume
            )
            repository.saveAll(listOf(tick))
            logger.info("stored tick for {}", dto.symbol)
        } catch (ex: Exception) {
            logger.error("failed to process tick", ex)
        }
    }
}

