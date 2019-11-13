package io.riguron.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.display.Display;
import io.riguron.exchange.exchange.client.BitmexClient;
import io.riguron.exchange.exchange.client.ExchangeClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BitmexClientTest extends ClientTest {

    @Test
    void whenGetSubscriptionMessageThenProperValueReturned() {
        List<String> messages = new BitmexClient(new ObjectMapper(), Arrays.asList("a", "b")).subscriptionMessages();
        assertEquals(1, messages.size());
        String json = messages.get(0);

        assertThat(json, hasJsonPath("$.op", equalTo("subscribe")));
        assertThat(json, hasJsonPath("$.args[*]", hasItems("trade:a", "trade:b")));
    }

    @Test
    void whenTradePassedToDisplayThenAccepted() {
        assertTrue(new BitmexClient(mock(ObjectMapper.class), Collections.emptyList()).display(
                value -> "trade"
        ));
    }

    @Test
    void whenDataReceivedThenDisplayed() {
        sendMessage("data");
        assertDisplay(display -> {
            assertDisplayed(display, 9180.5);
            assertDisplayed(display, 9182);
        });
    }

    private void assertDisplayed(Display display, double value) {
        verify(display).display(argThat(data -> data.getCurrencyName().equals("XBTUSD") && data.getPrice() == value));
    }

    @Test
    void whenSubscriptionMessageReceivedThenNotDisplayed() {
        sendMessage("subscribed");
        assertDisplay(Mockito::verifyNoInteractions);
    }

    @Test
    void whenWelcomeMessageReceivedThenNotDisplayed() {
        sendMessage("welcome");
        assertDisplay(Mockito::verifyNoInteractions);
    }

    @Override
    String name() {
        return "bitmex";
    }

    @Override
    ExchangeClient client() {
        return new BitmexClient(new ObjectMapper(), Collections.emptyList());
    }
}
