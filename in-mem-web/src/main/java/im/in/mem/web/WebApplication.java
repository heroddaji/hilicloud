/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web;

import im.in.mem.web.balancer.RestResource;
import im.in.mem.web.controller.FrameworkStatus;
import im.in.mem.web.jms.JmsHandler;
import im.in.mem.web.jms.JsonMessage;
import im.in.mem.web.others.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dai
 */
public class WebApplication extends Application<WebConfiguration> {

    private JmsHandler jmsHandler;
    private static WebApplication INSTANCE = null;
    private WebConfiguration configuration;
    private static FrameworkStatus frameworkStatus = new FrameworkStatus(1,"Framwork status");
    private final Logger log = LoggerFactory.getLogger(getClass());

    private WebApplication() {
    }

    public static WebApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebApplication();
        }

        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        getInstance().run(args);
    }

    public String getName() {
        return "web";
    }

    @Override
    public void initialize(Bootstrap<WebConfiguration> configBootstrap) {
        jmsHandler = new JmsHandler();
        configBootstrap.addBundle(jmsHandler.getActiveMQBundle());

    }

    @Override
    public void run(WebConfiguration _configuration, Environment environment) throws Exception {
        this.configuration = _configuration;
        
        final RestResource resource = new RestResource(configuration.getTemplate(), configuration.getDefaultName());
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
        
        log.info("init jms handler");
        jmsHandler.init(configuration);
        
        registerComponentsToController();
        
        
    }

    public JmsHandler getJmsManager() {
        return jmsHandler;
    }

    public static FrameworkStatus getFrameworkStatus() {
        return frameworkStatus;
    }
    
    private void registerComponentsToController(){
        
        JsonMessage registeringMessage = new JsonMessage();
        registeringMessage.setType(Constant.MESSAGETYPE_REGISTER);
        registeringMessage.setPaypload(getInstance().toString());
        
        
        if(configuration.getAppMode().equals(Constant.APPMODE_CONTROLLER)){
            //testing purpose
            jmsHandler.getSender().send((Session session) ->{
                TextMessage message = session.createTextMessage();
                message.setText("hrhr");
                message.setJMSCorrelationID("balancer");
                return message;
            });
        }
        else if(configuration.getAppMode().equals(Constant.APPMODE_BALANCER)){
            jmsHandler.getSender().send((Session session) ->{
                TextMessage message = session.createTextMessage();
                message.setText(registeringMessage.toString());
                message.setJMSCorrelationID("balancer");
                return message;
            });
        }
        else if(configuration.getAppMode().equals(Constant.APPMODE_SERVICE)){
            //register to controller
        }
        else if(configuration.getAppMode().equals(Constant.APPMODE_HYPERVISOR)){
            
        }
    }
}
