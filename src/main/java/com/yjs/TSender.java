package com.yjs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

public class TSender {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        //链接是对socket链接的抽象，并且负责版本协议的协商和认证。
        // 这里我们链接了本地服务器的代理，也就是localhost。
        // 如果我们想链接一个不同机器的代理，我们仅需要修改这里的名称或者ip地址即可。
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        //我们创建一个channel通道
        Channel channel = connection.createChannel();
        //为了发送，我们必须声明一个我们要发送信息的目标队列，然后我们往队列中发送消息。
        //声明一个队列是等幂的，如果队列不存在会创建一个，消息内容是一个字节数组，所以你可以想随心所欲的编码你的内容。
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello world!";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '"+message+"'");

        channel.close();
        connection.close();
    }

}
