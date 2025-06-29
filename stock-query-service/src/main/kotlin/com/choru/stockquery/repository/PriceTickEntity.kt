package com.choru.stockquery.repository

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "price_tick")
data class PriceTickEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val tradeAt: LocalDateTime,
    val symbol: String,
    val price: Double,
    val volume: Int
)
