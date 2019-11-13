package io.riguron.exchange.exchange.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.model.Response;
import io.riguron.exchange.model.liquid.LiquidResponse;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class LiquidClient implements ExchangeClient {

    private final ObjectMapper objectMapper;
    private List<String> pairs;

    public LiquidClient(ObjectMapper objectMapper, List<String> pairs) {
        this.objectMapper = objectMapper;
        this.pairs = pairs;
    }

    @Override
    public List<String> subscriptionMessages() {
        return pairs.stream().map(pair -> objectMapper
                .createObjectNode()
                .put("event", "pusher:subscribe")
                .set("data", objectMapper.createObjectNode().put("channel", String.format("product_cash_%s", pair)))
                .toString()
        ).collect(toList());

    }

    @Override
    public Class<? extends Response> rootNode() {
        return LiquidResponse.class;
    }

    @Override
    public boolean display(Function<String, String> pathAccessor) {
        return pathAccessor.apply("/event").equals("updated");
    }
}
