package com.choru.stock.adapter.out

import com.choru.stock.application.port.out.ExchangeClient
import com.choru.stock.adapter.out.dto.ExchangeTickDto
import com.choru.stock.domain.Money
import com.choru.stock.domain.PriceTick
import com.choru.stock.domain.Symbol
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal


@Component
class ExchangeHttpClient(
    @Value("\${exchange.base-url}") private val baseUrl: String,
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