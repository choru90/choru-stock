package com.choru.socketserver.domain

import java.time.LocalDateTime

/**
 * 주식 Tick 데이터 모델
 * symbol: 종목 코드
 * price: 현재 가격
 * timestamp: 발생 시각(epoch milli)
 * volume: 거래량
 */
data class Tick(
    val symbol: String,
    val price: Double,
    val timestamp: LocalDateTime,
    val volume: Long
)
