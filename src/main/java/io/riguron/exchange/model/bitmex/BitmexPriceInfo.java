package io.riguron.exchange.model.bitmex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.riguron.exchange.model.PriceInfo;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitmexPriceInfo implements PriceInfo {

    private double price;
    private String symbol;

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getCurrency() {
        return symbol;
    }
}
