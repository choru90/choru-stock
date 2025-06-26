package com.choru.socketserver

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TickPollingScheduler(
    private val restTemplate: RestTemplate,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val webSocketHandler: ws.TickWebSocketHandler,
    @Value("\${poll.url:http://localhost:8081/prices}") private val pollUrl: String,
    @Value("\${kafka.topic:ticks}") private val topic: String
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelayString = "\${poll.interval:1000}")
    fun poll() {
        try {
            val response = restTemplate.getForObject(pollUrl, String::class.java)
            if (response != null) {
                kafkaTemplate.send(topic, response)
                webSocketHandler.broadcast(response)
                logger.info("polled and sent tick data")
            }
        } catch (ex: Exception) {
            logger.error("polling failed", ex)
        }
    }
}

