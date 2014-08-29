/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.jms;

import com.kjetland.dropwizard.activemq.ActiveMQBundle;
import com.kjetland.dropwizard.activemq.ActiveMQSender;
import im.in.mem.web.WebConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsHandler {

    private final ActiveMQBundle activeMQBundle;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private WebConfiguration config;
    private ActiveMQSender sender;

    public JmsHandler() {
        this.activeMQBundle = new ActiveMQBundle();

    }

    public void init(WebConfiguration config) {
        this.config = config;
        
        log.info("create queue/topic in activemq");
        String destination = config.getQueueName();
        sender = activeMQBundle.createSender(destination, true);
        
        log.info("register listener on destination: " + config.getQueueName());
        JmsListener listener = new JmsListener();
        activeMQBundle.registerReceiver(destination, listener, JsonMessage.class, true);
    }

    public ActiveMQBundle getActiveMQBundle() {
        return activeMQBundle;
    }

    public ActiveMQSender getSender() {
        return sender;
    }

}
