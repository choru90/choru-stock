package com.choru.stockquery.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PriceTickRepository : JpaRepository<PriceTickEntity, Long> {
    fun findAllBySymbolAndTradeAtAfterOrderByTradeAtAsc(symbol: String, tradeAt: LocalDateTime): List<PriceTickEntity>
}
