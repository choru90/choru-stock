package com.choru.tickcollector.application.port.out

import com.choru.tickcollector.domain.PriceTick
import org.springframework.stereotype.Component

@Component
interface ExchangeClient {
    fun fetchLatestTicks(): List<PriceTick>
}