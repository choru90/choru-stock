package com.choru.stock.application.port.out

import com.choru.stock.domain.PriceTick

interface PriceTickRepository {
    fun  saveAll(ticks: List<PriceTick>)
}