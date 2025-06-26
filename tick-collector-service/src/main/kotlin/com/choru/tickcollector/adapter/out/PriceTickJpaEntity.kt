package com.choru.tickcollector.adapter.out

import com.choru.tickcollector.domain.Money
import com.choru.tickcollector.domain.Symbol
import jakarta.persistence.*
import jakarta.persistence.GenerationType
import java.time.Instant
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "price_tick")
@EntityListeners(AuditingEntityListener::class)
data class PriceTickEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false) val tradeAt: LocalDateTime,
    @Column(nullable = false) val symbol: Symbol,
    @Column(nullable = false) val price: Money,
    @Column(nullable = false) val volume: Int,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = null
)