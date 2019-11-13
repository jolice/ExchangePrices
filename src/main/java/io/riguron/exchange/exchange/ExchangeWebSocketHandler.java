package io.riguron.exchange.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.display.Data;
import io.riguron.exchange.display.Display;
import io.riguron.exchange.exchange.client.ExchangeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class ExchangeWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ExchangeClient exchangeClient;
    private final Display display;

    @Autowired
    public ExchangeWebSocketHandler(ObjectMapper mapper, ExchangeClient exchangeClient, Display display) {
        this.mapper = mapper;
        this.exchangeClient = exchangeClient;
        this.display = display;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established, subscribing");
        for (String subscriptionMessage : exchangeClient.subscriptionMessages()) {
            log.info("Sending subscription message: {}", subscriptionMessage);
            session.sendMessage(new TextMessage(subscriptionMessage));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode payloadRepresentation = mapper.readTree(message.getPayload());
        if (exchangeClient.display(path -> payloadRepresentation.at(path).asText())) {
            mapper.convertValue(payloadRepresentation, exchangeClient.rootNode()).getPrices().forEach(priceInfo -> display.display(
                    new Data(priceInfo.getCurrency(), priceInfo.getPrice())
            ));
        } else {
            log.info("Message received: {}", message.getPayload());
        }
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) {
        log.error("Pong message received: {}", message.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("A transport-level error has occurred", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Connection closed with status {}", status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}