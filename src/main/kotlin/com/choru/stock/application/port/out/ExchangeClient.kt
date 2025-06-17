package com.choru.stock.application.port.out

import com.choru.stock.domain.PriceTick

interface ExchangeClient {
    fun fetchLatestTicks(): List<PriceTick>
}