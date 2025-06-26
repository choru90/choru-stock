package com.choru.tickcollector.adapter.out

import com.choru.tickcollector.application.port.out.PriceTickRepository
import com.choru.tickcollector.domain.PriceTick
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface SpringDataPriceTickRepository: JpaRepository<PriceTickEntity, Long>

@Component
class JpaPriceTickRepository(
    private val repository: SpringDataPriceTickRepository
): PriceTickRepository {

    override fun saveAll(ticks: List<PriceTick>) {
        repository.saveAll(ticks.map { PriceTickEntity(
            tradeAt = it.tradedAt,
            symbol = it.symbol,
            price = it.price,
            volume = it.volume
        ) })
    }
}