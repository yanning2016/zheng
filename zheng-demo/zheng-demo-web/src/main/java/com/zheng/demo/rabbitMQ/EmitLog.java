package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/14.
 * 在创建连接后，声明交互
 * 如果当前没有队列被绑定到交换机，消息将被丢弃，因为没有消费者监听，这条消息将被丢弃
 */
public class EmitLog {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //声明使用的交换机
        // direct （直连）、topic （主题）、headers （标题）、fanout （分发）也有翻译为扇出的。
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //分发消息
        for (int i=0; i < 10; i++){
            String msg = "Hello World! "+ i;
            /**
             *  exchange：交换机名称
             routingKey：路由键
             props：消息属性字段，比如消息头部信息等等
             body：消息主体部分
             除此之外，还有mandatory和immediate这两个参数，鉴于RabbitMQ3.0不再支持immediate标志，因此我们重点讨论mandatory标志
             */
            //与之前不同的是 他不是讲消息发到默认的交换机中，而是发送到一个名为"logs"的fanout交换机
            //我们提供一个空字符串的routingKey， 它的功能被交换机的分发类型【fanout】代替
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

            System.out.println("Send :" + msg);

        }

        channel.close();
        connectionFactory.clone();
    }

}
