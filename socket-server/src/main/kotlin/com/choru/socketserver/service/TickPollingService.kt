package com.choru.socketserver.service

import com.choru.socketserver.domain.Tick
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Instant
import java.time.LocalDateTime

/**
 * mock 서버에서 주기적으로 각 종목의 tick 데이터를 polling 하여
 * 해당 종목을 구독 중인 클라이언트에게 웹소켓으로 전달하는 서비스.
 */
@Service
class TickPollingService(
    private val messagingTemplate: SimpMessagingTemplate,
    @Value("\${mock.base-url:http://localhost:8081}") private val mockBaseUrl: String
) {

    private val restTemplate = RestTemplate()
    private val symbols = listOf("AAPL", "GOOG")

    /**
     * 1초마다 mock 서버에서 각 종목의 tick 을 조회하여 브로드캐스트한다.
     */
    @Scheduled(fixedDelay = 1000)
    fun poll() {
        symbols.forEach { symbol ->
            // mock 서버 호출 URL 생성
            val url = "$mockBaseUrl/ticks/$symbol"
            // tick 데이터 요청 (응답이 없으면 다음 종목으로)
            val response = restTemplate.getForObject(url, TickResponse::class.java) ?: return@forEach
            val tick = Tick(
                symbol = symbol,
                price = response.price,
                timestamp = response.timestamp ?: LocalDateTime.now(),
                volume = response.volume
            )
            // 각 종목별 topic 으로 전송
            messagingTemplate.convertAndSend("/topic/ticks/$symbol", tick)
        }
    }

    /**
     * mock 서버 응답 DTO
     */
    data class TickResponse(
        val price: Double,
        val volume: Long,
        val timestamp: LocalDateTime?
    )
}
