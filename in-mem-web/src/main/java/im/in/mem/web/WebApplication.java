/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web;

import im.in.mem.web.core.FrameworkStatus;
import im.in.mem.web.health.TemplateHealthCheck;
import im.in.mem.web.jms.JmsManager;
import im.in.mem.web.resouces.RestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

/**
 *
 * @author dai
 */
public class WebApplication extends Application<WebConfiguration> {

    private JmsManager jmsManager;

    private static WebApplication INSTANCE = null;

    private static FrameworkStatus frameworkStatus = new FrameworkStatus(1,"Framwork status");

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
        jmsManager = new JmsManager();
        configBootstrap.addBundle(jmsManager.getActiveMQBundle());

    }

    @Override
    public void run(WebConfiguration configuration, Environment environment) throws Exception {
        final RestResource resource = new RestResource(configuration.getTemplate(), configuration.getDefaultName());
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
        jmsManager.setConfig(configuration);
        jmsManager.registerListener(configuration.getQueueName());

    }

    public JmsManager getJmsManager() {
        return jmsManager;
    }

    public static FrameworkStatus getFrameworkStatus() {
        return frameworkStatus;
    }
}
