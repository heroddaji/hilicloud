/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.core.jms;

import com.kjetland.dropwizard.activemq.ActiveMQBundle;
import com.kjetland.dropwizard.activemq.ActiveMQSender;
import freemarker.cache.WebappTemplateLoader;
import im.in.mem.web.Constant;
import im.in.mem.web.WebApplication;
import im.in.mem.web.WebConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsHandler {

    private final ActiveMQBundle activeMQBundle;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private ActiveMQSender sender;
    private String appMode;
    private WebConfiguration config;
    private ActiveMQSender senderToController;
    private ActiveMQSender senderToBalancer;
    private ActiveMQSender senderToHypervisor;
    private ActiveMQSender senderToServiceVM;

    public JmsHandler() {
        this.activeMQBundle = new ActiveMQBundle();
        
    }

    public void init(WebConfiguration _config) {
        this.config = _config;
        appMode = config.getAppMode();
        //create 4 senders
        log.info("create queue/topic in activemq");
        String destinationBalancer = config.getQueueName() + Constant.APPMODE_BALANCER;
        senderToBalancer = activeMQBundle.createSender(destinationBalancer, true);
        
        String destinationController = config.getQueueName() + Constant.APPMODE_CONTROLLER;
        senderToController = activeMQBundle.createSender(destinationController, true);
        
        String destinationServiceVM = config.getQueueName() + Constant.APPMODE_SERVICE;
        senderToServiceVM = activeMQBundle.createSender(destinationServiceVM, true);
        
        String destinationHypervisor = config.getQueueName() + Constant.APPMODE_HYPERVISOR;
        senderToHypervisor = activeMQBundle.createSender(destinationHypervisor, true);

        //create listener depend of appmode
        log.info("create listener on destination: " + destinationController);
        JmsListener listener = new JmsListener();

        if (config.getAppMode().equals(Constant.APPMODE_CONTROLLER)) {
            activeMQBundle.registerReceiver(destinationController, listener, JsonMessage.class, true);

        } else if (config.getAppMode().equals(Constant.APPMODE_BALANCER)) {
            activeMQBundle.registerReceiver(destinationBalancer, listener, JsonMessage.class, true);
            
        } else if (config.getAppMode().equals(Constant.APPMODE_SERVICE)) {
            activeMQBundle.registerReceiver(destinationServiceVM, listener, JsonMessage.class, true);
            
        } else if (config.getAppMode().equals(Constant.APPMODE_HYPERVISOR)) {
            activeMQBundle.registerReceiver(destinationHypervisor, listener, JsonMessage.class, true);
        }

    }

    public ActiveMQBundle getActiveMQBundle() {
        return activeMQBundle;
    }

    public ActiveMQSender getSender() {
        return sender;
    }

    public ActiveMQSender getSenderToController() {
        return senderToController;
    }

    public ActiveMQSender getSenderToBalancer() {
        return senderToBalancer;
    }

    public ActiveMQSender getSenderToHypervisor() {
        return senderToHypervisor;
    }

    public ActiveMQSender getSenderToServiceVM() {
        return senderToServiceVM;
    }

}
