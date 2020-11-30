package com.geekbrains.study.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Reader {
    private String topic;
    private String name;

    Reader(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    public void read(Channel channel) throws IOException {
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, MainApp.EXCHANGE_NAME, topic);

        System.out.println(" [" + name + "] Waiting for articles with topic key (" + topic + "):");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [" + name + "] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }

}
