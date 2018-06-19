# cryptoeval
Kotlin microservice to calculate the value of your cryptocurrency portfolio

Run the CryptoEvalServer.kt file to start up the server.

To invoke it, simply open the browser (or use an HTTP client) pointing to http://localhost:9000 and adding the crypto portfolio 
in query string (using the crypto symbol as key and quantity as value). F.i. assuming you want to calculate the value of a 
portfolio of 2.12 BTC and 12.1 ETH, simply point to

http://localhost:9000?btc=2.12&eth=12.1

The result of the invocation is a response containing only the value of the portfolio in USD.
