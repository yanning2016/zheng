package com.zheng.demo.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jondai on 2017/7/6.
 * 消息接受方
 */
public class Receiver {

    public static void main(String[] args){
        ConnectionFactory connectionFactory;

        Connection connection = null;

        Session session;

        Destination destination;

        MessageConsumer consumer;

        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

        try {
            connection = connectionFactory.createConnection();

            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue("FirstQueue");

            consumer = session.createConsumer(destination);

            while (true){
                TextMessage receive = (TextMessage) consumer.receive(100000);

                if(receive != null){
                    System.out.println(receive.getText());
                }else{
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (Throwable ignore) {

                }
            }
        }
    }

}
