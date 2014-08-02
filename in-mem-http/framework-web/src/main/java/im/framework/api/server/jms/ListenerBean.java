/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.server.jms;

import java.util.logging.Level;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ListenerBean {

    private static String remoteHost = "163.180.116.93";
    private static String remotePort = "61613";
    private static Logger logger = LoggerFactory.getLogger(ListenerBean.class);

    private void thread(Runnable runnble, boolean daemon) {
        Thread brokerThread = new Thread(runnble);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

    public void listen() {
        thread(new ActiveMQListener(), true);
    }

    public static class ActiveMQListener implements Runnable {

        @Override
        public void run() {
            try {

                String user = "admin";
                String password = "password";
                String host = remoteHost;
                int port = Integer.parseInt(remotePort);
                String destination = "/topic/event";

                StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
                factory.setBrokerURI("tcp://" + host + ":" + port);

                Connection connection = factory.createConnection(user, password);
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination dest = new StompJmsDestination(destination);

                MessageConsumer consumer = session.createConsumer(dest);
                long start = System.currentTimeMillis();
                long count = 1;
                System.out.println("Waiting for messages...");
                while (true) {
                    Message msg = consumer.receive();
                    if (msg instanceof TextMessage) {
                        String body = ((TextMessage) msg).getText();
                        if ("SHUTDOWN".equals(body)) {
                            long diff = System.currentTimeMillis() - start;
                            System.out.println(String.format("Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
                            break;
                        } else {
                            if (count != msg.getIntProperty("id")) {
                                System.out.println("mismatch: " + count + "!=" + msg.getIntProperty("id"));
                            }
                            count = msg.getIntProperty("id");

                            if (count == 0) {
                                start = System.currentTimeMillis();
                            }
                            if (count % 1000 == 0) {
                                System.out.println(String.format("Received %d messages.", count));
                            }
                            count++;
                        }

                    } else {
                        System.out.println("Unexpected message type: " + msg.getClass());
                    }
                }
                connection.close();
            } catch (JMSException e) {

            }
        }

    }

}
