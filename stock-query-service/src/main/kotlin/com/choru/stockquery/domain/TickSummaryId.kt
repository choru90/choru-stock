package com.choru.stockquery.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDateTime

@Embeddable
data class TickSummaryId(
    @Column(nullable = false)
    val symbol: String = "",
    @Column(nullable = false)
    val interval: String = "",
    @Column(name = "time", nullable = false)
    val time: LocalDateTime = LocalDateTime.MIN
) : Serializable
