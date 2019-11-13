package io.riguron.exchange.model;

import java.util.List;

public interface Response {

    List<? extends PriceInfo> getPrices();
}
