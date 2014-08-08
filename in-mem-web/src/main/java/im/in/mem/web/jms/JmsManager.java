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

public class JmsManager {

    private final ActiveMQBundle activeMQBundle;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private WebConfiguration config;

    public JmsManager() {
        this.activeMQBundle = new ActiveMQBundle();
    }

    public ActiveMQSender createSender() {

        String destination = config.getQueueName();

        ActiveMQSender activeMQSender = activeMQBundle.createSender(destination, false);
        return activeMQSender;

    }

    public void registerListener(String destination) {
        log.info("register listener on destination :" + destination);
        JmsListener listener = new JmsListener();
        activeMQBundle.registerReceiver(destination, listener, JmsMessage.class, true);
    }

    public ActiveMQBundle getActiveMQBundle() {
        return activeMQBundle;
    }

    public WebConfiguration getConfig() {
        return config;
    }

    public void setConfig(WebConfiguration config) {
        this.config = config;
    }

}
