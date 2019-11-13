package io.riguron.exchange.exchange.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.model.Response;
import io.riguron.exchange.model.bitmex.BitmexResponse;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BitmexClient implements ExchangeClient {

    private final ObjectMapper objectMapper;
    private final List<String> pairs;

    public BitmexClient(ObjectMapper objectMapper, List<String> pairs) {
        this.objectMapper = objectMapper;
        this.pairs = pairs;
    }

    @Override
    public List<String> subscriptionMessages() {
        return Collections.singletonList(
                objectMapper
                        .createObjectNode()
                        .put("op", "subscribe")
                        .set("args", pairs.stream().reduce(objectMapper.createArrayNode(), (node, s) -> node.add(String.format("trade:%s", s)), (l, r) -> r))
                        .toString()
        );
    }

    @Override
    public Class<? extends Response> rootNode() {
        return BitmexResponse.class;
    }


    @Override
    public boolean display(Function<String, String> pathAccessor) {
        return pathAccessor.apply("/table").equals("trade");
    }


}
