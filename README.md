# ExchangePrices

Test assignment for Java Developer position at Profluent Group

>#1 Quoine Exchange (Japan) - https://developers.liquid.com/
>
>Implement a connection to the exchange and fetch real-time prices for the following currency pairs pairs: BTCJPY, BTCUSD
>
>#2 The same for BitMEX - https://www.bitmex.com/app/apiOverview
>
>Currency pairs: #XBTUSD and any other (choose one).
>
>1, 2 - just print prices to console(std output)

## Running

First, clone the project:

```bash
git clone git@github.com:riguron/ExchangePrices.git
cd ExchangePrices
```

Then run using Spring Boot Maven Plugin, specifying the exchange name (either ```liquid``` or ```bitmex```):

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=$EXCHANGE_NAME
```

For example:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=bitmex
```



