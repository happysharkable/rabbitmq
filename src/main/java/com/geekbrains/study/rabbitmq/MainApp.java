package com.geekbrains.study.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Arrays;

public class MainApp {
    public static final String EXCHANGE_NAME = "articles";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        Reader r1 = new Reader("Java reader", "code.java");
        Reader r2 = new Reader("C++ reader", "code.c++");
        Reader r3 = new Reader("PHP reader", "code.php");
        Reader r4 = new Reader("All topics reader", "code.*");

        for (Reader r : Arrays.asList(r1, r2, r3, r4)) {
            r.read(channel);
        }
    }
}
