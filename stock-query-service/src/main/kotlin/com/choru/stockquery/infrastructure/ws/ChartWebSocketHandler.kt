package com.choru.stockquery.infrastructure.ws

import com.choru.stockquery.application.dto.BarDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class ChartWebSocketHandler : TextWebSocketHandler() {
    private val sessions = mutableSetOf<WebSocketSession>()
    private val mapper = jacksonObjectMapper()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions += session
        logger.info("ws connected: {}", session.id)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: org.springframework.web.socket.CloseStatus) {
        sessions -= session
        logger.info("ws disconnected: {}", session.id)
    }

    fun broadcast(bar: BarDto) {
        val payload = mapper.writeValueAsString(bar)
        sessions.forEach { it.sendMessage(TextMessage(payload)) }
    }
}
