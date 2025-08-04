package com.choru.socketserver.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * 웹소켓(STOMP) 설정 클래스
 * 각 종목별 토픽으로 메시지를 전달하기 위해 간단한 메시지 브로커를 활성화한다.
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
) : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // "topic"으로 시작하는 경로로 메시지를 브로드캐스팅한다.
        registry.enableSimpleBroker("/topic")
        // 클라이언트가 서버로 메시지를 보낼 때 사용할 접두어
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // 웹소켓 연결 엔드포인트. SockJS를 사용하여 브라우저 호환성을 높인다.
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS()
    }
}
