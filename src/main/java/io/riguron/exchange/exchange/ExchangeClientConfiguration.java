package io.riguron.exchange.exchange;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.exchange.client.BitmexClient;
import io.riguron.exchange.exchange.client.ExchangeClient;
import io.riguron.exchange.exchange.client.LiquidClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class ExchangeClientConfiguration {

    @Value("#{'${pairs}'.split(',')}")
    private List<String> pairs;

    @Bean
    @Profile("liquid")
    public ExchangeClient exchangeClient() {
        return new LiquidClient(mapper(), pairs);
    }

    @Bean
    @Profile("bitmex")
    public ExchangeClient bitmexClient() {
        return new BitmexClient(mapper(), pairs);
    }


    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

}
