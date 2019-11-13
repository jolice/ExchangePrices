package io.riguron.exchange.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class WebSocketConnectionLifecycle {

    private final WebSocketConnectionManager webSocketConnectionManager;

    @Autowired
    public WebSocketConnectionLifecycle(WebSocketConnectionManager webSocketConnectionManager) {
        this.webSocketConnectionManager = webSocketConnectionManager;
    }

    @PostConstruct
    public void openConnection() {
        webSocketConnectionManager.start();
    }

    @PreDestroy
    public void closeConnection() {
        webSocketConnectionManager.stop();
    }
}
