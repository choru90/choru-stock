package com.choru.stock.application.port.out

import com.choru.stock.domain.PriceTick
import org.springframework.stereotype.Component

@Component
interface ExchangeClient {
    fun fetchLatestTicks(): List<PriceTick>
}