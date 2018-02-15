package it.mokabyte.kotlin.cryptoeval

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.queries
import org.http4k.format.Jackson.parse
import org.http4k.server.Jetty
import org.http4k.server.asServer
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


fun List<Quote>.findQuote(symbol: String) =
        this.find { it.symbol.toUpperCase() == symbol.toUpperCase() }?.priceUsd ?: BigDecimal.ZERO


fun endpoint(request: Request) : Response {
    val quotes = retrieveQuotes()
    val portfolio = request.uri
            .queries()
            .map { ( symbol, quantity) -> BigDecimal(quantity) * quotes.findQuote(symbol) }
            .fold(BigDecimal.ZERO, { first, second ->  first + second })

    return Response(OK).body("$portfolio")
}


fun main(args: Array<String>) {
    ::endpoint.asServer(Jetty(9000)).start()
}

