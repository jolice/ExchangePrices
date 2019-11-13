package io.riguron.exchange.model.bitmex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.riguron.exchange.model.PriceInfo;
import io.riguron.exchange.model.Response;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitmexResponse implements Response {

    private List<BitmexPriceInfo> data;

    @Override
    public List<? extends PriceInfo> getPrices() {
        return data;
    }
}
