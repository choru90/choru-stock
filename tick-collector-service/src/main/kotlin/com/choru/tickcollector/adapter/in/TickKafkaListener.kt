package com.choru.tickcollector.adapter.`in`

import com.fasterxml.jackson.module.kotlin.readValue
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
    private val priceTickRepository: PriceTickRepository,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["ticks"], groupId = "tick-collector-group")
    fun listen(message: String) {
        try {
            val dtoList = objectMapper.readValue<List<ExchangeTickDto>>(message)
            val ticks = dtoList.map { dto ->
                PriceTick(
                    symbol = Symbol(dto.symbol),
                    tradedAt = dto.timestamp,
                    price = Money(BigDecimal(dto.price)),
                    volume = dto.volume
                )
            }
            priceTickRepository.saveAll(ticks)
            logger.info("saved {} ticks", ticks.size)
        } catch (ex: Exception) {
            logger.error("failed to process kafka message", ex)
        }
    }
}
