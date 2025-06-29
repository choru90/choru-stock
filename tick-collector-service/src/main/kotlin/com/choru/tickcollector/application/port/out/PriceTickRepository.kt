package com.choru.tickcollector.application.port.out

import com.choru.tickcollector.domain.PriceTick

interface PriceTickRepository {
    fun  saveAll(ticks: List<PriceTick>)
}
