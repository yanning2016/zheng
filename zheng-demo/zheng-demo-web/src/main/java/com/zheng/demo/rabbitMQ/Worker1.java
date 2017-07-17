package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/13.
 * 消费者1
 */
public class Worker1 {
    public static final String QUEUE_NAME = NewTask.QUEUE_NAME;

    public static void main(String[] agrs) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("Worker1 [*] Waiting for messages. To exit press CTRL+C");

        //每次从队列中获取消息数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");

                System.out.println("Worker1 received:" + msg);

                try {
                    doWork(msg);
                }finally {
                    System.out.println("Worker1 Done");
                    //消息处理确认完成
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        //消息消费者完成确认
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }


    private static void doWork(String task){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
