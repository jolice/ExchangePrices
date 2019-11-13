package io.riguron.exchange.model.liquid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.riguron.exchange.model.PriceInfo;
import io.riguron.exchange.model.Response;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LiquidResponse implements Response {

    @JsonDeserialize(using = LiquidPriceDeserializer.class)
    private List<LiquidPriceInfo> data;

    @Override
    public List<? extends PriceInfo> getPrices() {
        return data;
    }
}
