package com.choru.stock.domain

import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime

@JvmInline value class Symbol(val value: String)
@JvmInline value class Money(val value: BigDecimal)

data class PriceTick(
    val symbol: Symbol,
    val tradedAt: LocalDateTime,
    val price: Money,
    val volume: Int,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
