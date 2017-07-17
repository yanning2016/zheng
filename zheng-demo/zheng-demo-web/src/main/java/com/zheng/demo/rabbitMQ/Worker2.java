package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/14.
 */
public class Worker2 {

    public static final String QUEUE_NAME = NewTask.QUEUE_NAME;


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);

        Connection connection = connectionFactory.newConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //每次从消息列表中获取一个消息
        channel.basicQos(1);


        final Consumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Worker2 Received:" + msg);

                doWork(msg);

                //确认处理完消息
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 消息消费完成确认
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }

    private static void doWork(String task){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
