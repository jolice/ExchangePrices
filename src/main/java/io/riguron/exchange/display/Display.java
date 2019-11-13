package io.riguron.exchange.display;

import org.springframework.stereotype.Component;

@Component
public class Display {

    public void display(Data data) {
        System.out.printf("%s: %s\n", data.getCurrencyName(), data.getPrice());
    }
}