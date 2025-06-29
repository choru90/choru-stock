package com.choru.tickcollector.adapter.`in`

import com.choru.tickcollector.adapter.out.dto.ExchangeTickDto
import com.choru.tickcollector.application.port.out.PriceTickRepository
import com.choru.tickcollector.domain.Money
import com.choru.tickcollector.domain.PriceTick
import com.choru.tickcollector.domain.Symbol
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class TickKafkaListener(
    private val priceTickRepository: PriceTickRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val objectMapper = jacksonObjectMapper()

    @KafkaListener(topics = ["\${kafka.topic:ticks}"])
    fun listen(message: String) {
        try {
            val dtoList: List<ExchangeTickDto> = objectMapper.readValue(message)
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
