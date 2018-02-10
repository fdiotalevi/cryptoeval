package it.mokabyte.kotlin.cryptoeval

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.parse
import java.math.BigDecimal


data class Quote(val symbol: String, val priceUsd: BigDecimal)


fun retrieveQuotes() : List<Quote> {

    val client = ApacheClient()
    val request = Request(Method.GET, "https://api.coinmarketcap.com/v1/ticker/?limit=20")
    val jsonResponse = parse(client(request).bodyString())

    fun toQuote(jsonQuote: JsonNode) = Quote(
            symbol = jsonQuote["symbol"].asText(),
            priceUsd = jsonQuote["price_usd"].asText().toBigDecimal()
    )

    return jsonResponse.map(::toQuote)
}


fun main(args: Array<String>) {
    println(retrieveQuotes())
}

