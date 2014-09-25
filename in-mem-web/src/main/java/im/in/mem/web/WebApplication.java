/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import im.in.mem.web.balancer.Balancer;
import im.in.mem.web.balancer.rest.HiLiCloudRest;
import im.in.mem.web.balancer.rest.UsersRest;
import im.in.mem.web.controller.Controller;
import im.in.mem.web.core.Framework;
import im.in.mem.web.core.Root;
import im.in.mem.web.dao.User;
import im.in.mem.web.jms.JmsHandler;
import im.in.mem.web.jms.JsonMessage;
import im.in.mem.web.others.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dai
 */
public class WebApplication extends Application<WebConfiguration> {

    private JmsHandler jmsHandler;
    private static WebApplication INSTANCE = null;
    private WebConfiguration config;
    private static Framework frameworkStatus = new Framework(1, "Framwork status");
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
        log.info("bootstrap  jms handler");        
        jmsHandler = new JmsHandler();
        configBootstrap.addBundle(jmsHandler.getActiveMQBundle()); 
    }

    @Override
    public void run(WebConfiguration _config, Environment environment) throws Exception {
        this.config = _config;
        jmsHandler.init(_config);
        final HiLiCloudRest hiliResource = new HiLiCloudRest(config.getTemplate(), config.getDefaultName());
        final UsersRest userResource = new UsersRest();
        
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(config.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(hiliResource);
        environment.jersey().register(userResource);
        
        //TODO: handle if activemq is working or not
        // or add embbeded activemq server
        
        registerComponents();
        
        Morphia morphia = new Morphia();
        MongoClient mgClient = new MongoClient();
        Datastore ds = morphia.createDatastore(mgClient, "hilicloud");
        ds.ensureIndexes();
        ds.ensureCaps();
        
        User user = new User("dai", "admin", "aaa");
        User user2 = new User("dai2", "admin", "aaa");
        ds.save(user);
        ds.save(user2);        

    }

    public static Framework getFrameworkStatus() {
        return frameworkStatus;
    }

    private void registerComponents() {
        //Controller.Haha h = new Controller.Haha();
        Controller.Shaha h2 = new Controller.Shaha();
        log.info("create resiter message");
        JsonMessage registeringMessage = new JsonMessage();
        registeringMessage.setType(Constant.MESSAGETYPE_REGISTER);
        registeringMessage.setPaypload(getInstance().toString());

        if (config.getAppMode().equals(Constant.APPMODE_CONTROLLER)) {
            log.info("init controller");
            Controller controller = new Controller();            
            controller.init(config);
            controller.setJmsHandler(jmsHandler);
            registeringMessage.setRole(Constant.APPMODE_CONTROLLER);
            controller.getJmsHandler().getSenderToController().send(registeringMessage);

        } else if (config.getAppMode().equals(Constant.APPMODE_BALANCER)) {
            log.info("init balancer");
            Balancer balancer = new Balancer();            
            balancer.init(config);
            balancer.setJmsHandler(jmsHandler);

            registeringMessage.setRole(Constant.APPMODE_BALANCER);
            balancer.getJmsHandler().getSenderToController().send(registeringMessage);

        } else if (config.getAppMode().equals(Constant.APPMODE_SERVICE)) {
            registeringMessage.setRole(Constant.APPMODE_SERVICE);
            jmsHandler.getSenderToController().send(registeringMessage);
        } else if (config.getAppMode().equals(Constant.APPMODE_HYPERVISOR)) {
            registeringMessage.setRole(Constant.APPMODE_HYPERVISOR);
            jmsHandler.getSenderToController().send(registeringMessage);
        }

    }

    public WebConfiguration getConfiguration() {
        return config;
    }
}
