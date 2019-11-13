package io.riguron.exchange.exchange.client;

import io.riguron.exchange.model.Response;

import java.util.List;
import java.util.function.Function;

public interface ExchangeClient {

    List<String> subscriptionMessages();

    Class<? extends Response> rootNode();

    boolean display(Function<String, String> pathAccessor);

}
