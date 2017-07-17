package com.zheng.demo.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jondai on 2017/7/12.
 * 发送端
 */
public class Sender {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工场
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //queue 的定义具有幂等性（一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相等）
        // 因此定义的queue已经存在，不会重复定义，且不能修改。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello world";

        //  第一个参数就是交换器的名称。如果输入“”空字符串，表示使用默认的匿名交换器。
        //  第二个参数是【routingKey】路由线索
        //  匿名交换器规则：
        //  发送到routingKey名称对应的队列。
        channel.basicPublish("", QUEUE_NAME,null, msg.getBytes());

        System.out.println(" [x] Sent '" + msg + "'");

        channel.close();
        connection.close();
    }


}
