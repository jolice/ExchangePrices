package io.riguron.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.exchange.ExchangeWebSocketHandler;
import io.riguron.exchange.display.Display;
import io.riguron.exchange.exchange.client.ExchangeClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.util.ResourceUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;

abstract class ClientTest {

    private Display display;
    private ExchangeWebSocketHandler exchangeWebSocketHandler;

    @BeforeEach
    void createHandler() {
        this.display = mock(Display.class);
        this.exchangeWebSocketHandler = new ExchangeWebSocketHandler(
                new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY),
                client(),
                display
        );
    }

    void sendMessage(String jsonFile) {
        try {
            exchangeWebSocketHandler.handleMessage(mock(WebSocketSession.class), message(jsonFile));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    void assertDisplay(Consumer<Display> consumer) {
        consumer.accept(display);
    }

    private TextMessage message(String jsonFile) {
        try {
            return new TextMessage(
                    Files.readAllBytes(
                            ResourceUtils.getFile(getClass().getResource(String.format("/%s/%s.json", name(), jsonFile))).toPath()
                    )
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    abstract String name();

    abstract ExchangeClient client();

}
