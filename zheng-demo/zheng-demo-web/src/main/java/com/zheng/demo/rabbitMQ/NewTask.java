package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/13.
 * 生成者P
 *
 */
public class NewTask {
    public static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * Name queueName
         Durable（消息代理重启后，队列依旧存在）
         Exclusive（只被一个连接（connection）使用，而且当连接关闭后队列即被删除）
         Auto-delete（当最后一个消费者退订后即被删除）
         Arguments（一些消息代理用他来完成类似与TTL的某些额外功能）
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        //分发消息
        for (int i = 0; i < 5; i++){
            String msg = "Hello World : " + i;

            //消息持久化
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());

            System.out.println("Send : "+ msg);
        }

        channel.close();
        connection.close();
    }

}
