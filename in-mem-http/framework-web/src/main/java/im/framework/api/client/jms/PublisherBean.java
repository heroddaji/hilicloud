/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.client.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

public class PublisherBean {

    private static String remoteHost = "163.180.116.93";
    private static String remotePort = "61613";
    private static Logger logger = Logger.getLogger("JmsService");

    public void register() {
        try {

            String user = "admin";
            String password = "password";
            String host = remoteHost;
            int port = Integer.parseInt(remotePort);
            String destination = "/topic/event";

            int messages = 10000;
            int size = 256;

            String DATA = "abcdefghijklmnopqrstuvwxyz";
            String body = "";
            for (int i = 0; i < size; i++) {
                body += DATA.charAt(i % DATA.length());
            }

            StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
            factory.setBrokerURI("tcp://" + host + ":" + port);

            Connection connection = factory.createConnection(user, password);
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new StompJmsDestination(destination);
            MessageProducer producer = session.createProducer(dest);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 1; i <= messages; i++) {
                TextMessage msg = session.createTextMessage(body);
                msg.setIntProperty("id", i);
                producer.send(msg);
                if ((i % 1000) == 0) {
                    logger.info(String.format("Sent %d messages", i));
                }
            }

            //producer.send(session.createTextMessage("SHUTDOWN"));
            connection.close();
        } catch (JMSException e) {

        }
    }

}
