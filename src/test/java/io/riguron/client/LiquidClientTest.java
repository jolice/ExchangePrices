package io.riguron.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.riguron.exchange.exchange.client.ExchangeClient;
import io.riguron.exchange.exchange.client.LiquidClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LiquidClientTest extends ClientTest {

    @Test
    void whenGetSubscriptionMessageThenProperValueReturned() {

        List<String> subscriptionMessages = new LiquidClient(new ObjectMapper(), Arrays.asList("a", "b")).subscriptionMessages();

        String first = subscriptionMessages.get(0);

        assertThat(first, hasJsonPath("$.event", equalTo("pusher:subscribe")));
        assertThat(first, hasJsonPath("$.data.channel", equalTo("product_cash_a")));

        String second = subscriptionMessages.get(1);

        assertThat(second, hasJsonPath("$.event", equalTo("pusher:subscribe")));
        assertThat(second, hasJsonPath("$.data.channel", equalTo("product_cash_b")));
    }

    @Test
    void whenUpdatedPassedToDisplayThenAccepted() {
        assertTrue(
                new LiquidClient(mock(ObjectMapper.class), Collections.emptyList()).display(value -> "updated")
        );
    }


    @Test
    void whenDataReceivedThenDisplayed() {
        sendMessage("data");
        assertDisplay(display -> verify(display).display(argThat(data -> data.getCurrencyName().equals("BTCUSD") && data.getPrice() == 9140.88)));
    }

    @Test
    void whenEstablishedMessageReceivedThenNotDisplayed() {
        sendMessage("established");
        assertDisplay(Mockito::verifyNoInteractions);
    }


    @Test
    void whenSubscribedMessageReceivedThenNotDisplayed() {
        sendMessage("subscribed");
        assertDisplay(Mockito::verifyNoInteractions);
    }


    @Override
    String name() {
        return "liquid";
    }

    @Override
    ExchangeClient client() {
        return new LiquidClient(new ObjectMapper(), Collections.emptyList());
    }
}
