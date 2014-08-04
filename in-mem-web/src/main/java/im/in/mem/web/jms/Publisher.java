/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.jms;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Publisher {
    
    private static String remoteHost = "163.180.116.93";
    private static String remotePort = "61613";
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);
    
    @GET
    @Path("rest/register")
    public void register() {
//        try {
//            
//            String user = "admin";
//            String password = "password";
//            String host = remoteHost;
//            int port = Integer.parseInt(remotePort);
//            String destination = "/topic/event";
//            
//            int messages = 10000;
//            int size = 256;
//            
//            String DATA = "abcdefghijklmnopqrstuvwxyz";
//            String body = "";
//            for (int i = 0; i < size; i++) {
//                body += DATA.charAt(i % DATA.length());
//            }
//            
//            StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
//            factory.setBrokerURI("tcp://" + host + ":" + port);
//            
//            Connection connection = factory.createConnection(user, password);
//            connection.start();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Destination dest = new StompJmsDestination(destination);
//            MessageProducer producer = session.createProducer(dest);
//            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//            
//            for (int i = 1; i <= messages; i++) {
//                TextMessage msg = session.createTextMessage(body);
//                msg.setIntProperty("id", i);
//                producer.send(msg);
//                if ((i % 1000) == 0) {
//                    logger.info("send message " + i);
//                }
//            }
//
//            //producer.send(session.createTextMessage("SHUTDOWN"));
//            connection.close();
//        } catch (JMSException e) {
//            
//        }
    }
    
}
