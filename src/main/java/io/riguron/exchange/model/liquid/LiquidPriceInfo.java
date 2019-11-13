package io.riguron.exchange.model.liquid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.riguron.exchange.model.PriceInfo;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LiquidPriceInfo implements PriceInfo {

    @JsonProperty("last_traded_price")
    private double price;

    @JsonProperty("currency_pair_code")
    private String currency;

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getCurrency() {
        return currency;
    }
}
