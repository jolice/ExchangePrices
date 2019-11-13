package io.riguron.exchange;

import io.riguron.exchange.display.Data;
import io.riguron.exchange.display.Display;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayTest {

    @Test
    void display() {

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));

        Display display = new Display();
        display.display(new Data("BTC", 2D));
        display.display(new Data("USD", 5D));

        String allWrittenLines = new String(bo.toByteArray());

        assertEquals("BTC: 2.0\nUSD: 5.0\n", allWrittenLines);
    }
}