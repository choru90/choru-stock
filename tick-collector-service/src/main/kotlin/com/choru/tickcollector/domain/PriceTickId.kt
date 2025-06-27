package com.choru.tickcollector.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDateTime

@Embeddable
data class PriceTickId(
    @Column(name = "symbol", nullable = false)
    val symbol: Symbol = Symbol(""),

    @Column(name = "trade_at", nullable = false)
    val tradeAt: LocalDateTime = LocalDateTime.now()
): Serializable{
    @Suppress("unused")
    protected constructor() : this(
        symbol = Symbol(""),
        tradeAt = LocalDateTime.MIN
    )
}

