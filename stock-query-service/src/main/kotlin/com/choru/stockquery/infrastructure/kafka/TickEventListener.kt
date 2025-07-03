package com.choru.stockquery.infrastructure.kafka

import com.choru.stockquery.application.ChartService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TickEventListener(
    private val service: ChartService
) {
    private val mapper = jacksonObjectMapper()

    @KafkaListener(topics = ["tick-events"], containerFactory = "kafkaListenerContainerFactory")
    fun handle(message: String) {
        val event: TickEvent = mapper.readValue(message)
        service.update(event)
    }
}
