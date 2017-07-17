package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/14.
 */
public class ReceiveLogs2 {
    public static final String EXCHANGE_NAME = EmitLog.EXCHANGE_NAME;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //交换机名称、交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 在Java客户端，提供queueDeclare()为我们创建一个非持久化、独立、自动删除的队列名称。
        String queueName = channel.queueDeclare().getQueue();
        // 将我们的队列跟交换器进行绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("Waiting...");


        Consumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received "+ msg);
            }
        };

        channel.basicConsume(queueName, true, defaultConsumer);
    }

}
