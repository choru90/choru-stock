package com.choru.stock.domain

import java.math.BigDecimal
import java.time.Instant

@JvmInline value class Symbol(val value: String)
@JvmInline value class Money(val value: BigDecimal)

data class PriceTick(
    val symbol: String,
    val tradedAt: Instant,
    val price: Money,
    val volume: Long,
)
