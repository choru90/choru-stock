package com.choru.stockquery.domain

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tick_summary")
data class TickSummary(
    @EmbeddedId
    val id: TickSummaryId,
    @Column(nullable = false)
    val open: BigDecimal,
    @Column(nullable = false)
    val high: BigDecimal,
    @Column(nullable = false)
    val low: BigDecimal,
    @Column(nullable = false)
    val close: BigDecimal,
    @Column(nullable = false)
    val volume: Long
)
