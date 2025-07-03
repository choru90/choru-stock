package com.choru.tickcollector.adapter.out

import com.choru.tickcollector.application.port.out.ExchangeClient
import com.choru.tickcollector.adapter.out.dto.ExchangeTickDto
import com.choru.tickcollector.domain.Money
import com.choru.tickcollector.domain.PriceTick
import com.choru.tickcollector.domain.Symbol
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal


@Component
class ExchangeHttpClient(
    @Value("\${poll.url:http://localhost:8081/prices}") private val baseUrl: String,
    private val restTemplate: RestTemplate
): ExchangeClient {

    override fun fetchLatestTicks(): List<PriceTick> {
        val url = "$baseUrl/prices"
        val dtoList = restTemplate.getForObject(url, Array<ExchangeTickDto>::class.java) ?: return emptyList()
        return dtoList.map {it.toDomain()}
    }

    private fun ExchangeTickDto.toDomain() = PriceTick(
        symbol   = Symbol(symbol),
        tradedAt = timestamp,
        price    = Money(BigDecimal(price)),
        volume   = volume
    )
}