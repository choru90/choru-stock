package com.choru.stock.adapter.out.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ExchangeTickDto(
    val symbol: String,
    val price: Double,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val timestamp: LocalDateTime,
    val volume: Int,
)
