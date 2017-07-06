package com.zheng.demo.activeMQ;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jondai on 2017/7/6.
 * 消息发送方
 */
public class Sender {
    private static final int SEND_NUMBER = 5;

    public static void main(String[] args){
        //连接工厂 JMS用它来创建连接
        ConnectionFactory connectionFactory;
        //JMS客户端到provide的连接
        Connection connection;
        //一个发送或者接受消息的线程
        Session session = null;
        //消息目的地，这个消息发送给谁
        Destination destination;
        //消息发送者
        MessageProducer producer;

        //构造ConnectionFactory，此处采用ActivityMQ的Jar的实现
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL);


        //从工厂中获得实例

        try {
            connection = connectionFactory.createConnection();
            //启动
            connection.start();

            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //消息目的地
            destination = session.createQueue("FirstQueue");
            producer = session.createProducer(destination);

            //设置不持久化，此处学习，实际根据项目决定
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, producer);
            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }



    public static void sendMessage(Session session, MessageProducer producer) throws JMSException {
        for (int i = 0; i < SEND_NUMBER; i++){
            TextMessage message = session.createTextMessage("ActiveMq 发送的消息" + i);

            // 发送消息到目的地方
            System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);
            producer.send(message);
        }
    }
}
