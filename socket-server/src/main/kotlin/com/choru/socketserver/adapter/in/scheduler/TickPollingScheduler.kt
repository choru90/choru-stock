package com.choru.socketserver.adapter.`in`.scheduler

import com.choru.socketserver.adapter.out.ExchangeHttpClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TickPollingScheduler(
    private val client: ExchangeHttpClient,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val webSocketHandler: com.choru.socketserver.adapter.`in`.ws.TickWebSocketHandler,
    @Value("\${kafka.topic:ticks}") private val topic: String
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelayString = "\${poll.interval:1000}")
    fun poll() {
        try {
            val ticks = client.fetchTicks()
            ticks.forEach { tick ->
                val json = objectMapper.writeValueAsString(tick)
                kafkaTemplate.send(topic, json)
                webSocketHandler.broadcast(json)
            }
            if (ticks.isNotEmpty()) {
                logger.info("sent {} ticks", ticks.size)
            }
        } catch (ex: Exception) {
            logger.error("polling failed", ex)
        }
    }
}

