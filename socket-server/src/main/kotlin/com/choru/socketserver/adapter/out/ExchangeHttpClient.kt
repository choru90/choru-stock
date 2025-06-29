package com.choru.socketserver.adapter.out

import com.choru.socketserver.domain.ExchangeTickDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ExchangeHttpClient(
    @Value("\${exchange.base-url:http://localhost:8084}") private val baseUrl: String,
    private val restTemplate: RestTemplate
) {
    fun fetchTicks(): List<ExchangeTickDto> {
        val url = "$baseUrl/prices"
        return restTemplate.getForObject(url, Array<ExchangeTickDto>::class.java)?.toList() ?: emptyList()
    }
}
