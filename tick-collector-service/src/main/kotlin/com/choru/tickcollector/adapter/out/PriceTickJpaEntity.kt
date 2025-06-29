package com.choru.tickcollector.adapter.out

import com.choru.tickcollector.domain.Money
import com.choru.tickcollector.domain.PriceTickId
import jakarta.persistence.*
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "price_tick")
@EntityListeners(AuditingEntityListener::class)
data class PriceTickEntity(

    @EmbeddedId
    val id: PriceTickId,

    @Column(nullable = false) val price: Money,
    @Column(nullable = false) val volume: Int,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = null
)