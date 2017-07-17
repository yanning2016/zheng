package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/13.
 */
public class Receiver {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 我们在接收端也定义了hello队列。这是为了确保，如果接收端先启动的时候，队列已经存在。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("Receiver Msg:"+ msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
