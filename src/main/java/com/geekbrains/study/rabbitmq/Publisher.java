package com.geekbrains.study.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Publisher {
    private static final int ARTICLES_AMOUNT = 10;
    private static final String EXCHANGE_NAME = "articles";
    private static final List<String> TOPICS = new ArrayList<>(Arrays.asList("code.java", "code.c++", "code.php"));

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String topic;
            String message = "[article text]";

            // "публикуем" ARTICLES_AMOUNT статей на случайные темы из списка
            for (int i = 0; i < ARTICLES_AMOUNT; i++) {
                topic = TOPICS.get((int) ((Math.random() * 10) % TOPICS.size()));
                channel.basicPublish(EXCHANGE_NAME, topic, false,null, message.getBytes("UTF-8"));
                System.out.println("Sent " + topic + " number [" + i + "]; " + message);
                Thread.sleep(1000);
            }
        }
    }
}
