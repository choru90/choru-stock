package com.choru.socketserver.ws

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class TickWebSocketHandler : TextWebSocketHandler() {
    private val sessions = mutableSetOf<WebSocketSession>()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions += session
        logger.info("client connected: {}", session.id)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: org.springframework.web.socket.CloseStatus) {
        sessions -= session
        logger.info("client disconnected: {}", session.id)
    }

    fun broadcast(message: String) {
        sessions.forEach { it.sendMessage(TextMessage(message)) }
    }
}
