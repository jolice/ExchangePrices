package io.riguron.exchange.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public WebSocketConnectionManager connectionManager(@Value("${websocket.endpoint}") String connectionUrl, @Value("${payload.length.max}") int maxLength, WebSocketHandler webSocketHandler) {
        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        webSocketContainer.setDefaultMaxTextMessageBufferSize(maxLength);
        return new WebSocketConnectionManager(new StandardWebSocketClient(webSocketContainer), webSocketHandler, connectionUrl);

    }


}
