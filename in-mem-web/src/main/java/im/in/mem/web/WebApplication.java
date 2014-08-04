/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web;


import com.kjetland.dropwizard.activemq.ActiveMQBundle;
import com.kjetland.dropwizard.activemq.ActiveMQSender;
import im.in.mem.web.core.Status;
import im.in.mem.web.health.TemplateHealthCheck;
import im.in.mem.web.jms.Publisher;
import im.in.mem.web.resouces.RestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.Optional;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author dai
 */
public class WebApplication extends Application<WebConfiguration> {

    private ActiveMQBundle activeMQBundle;

    public static void main(String[] args) throws Exception {
        new WebApplication().run(args);
    }

    public String getName() {
        return "web";
    }

    @Override
    public void initialize(Bootstrap<WebConfiguration> configBootstrap) {
        
        this.activeMQBundle = new ActiveMQBundle();        
        configBootstrap.addBundle(activeMQBundle);
    }

    @Override
    public void run(WebConfiguration configuration, Environment environment) throws Exception {
        final RestResource resource = new RestResource(configuration.getTemplate(), configuration.getDefaultName());
        Publisher publisher = new Publisher();
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
        environment.jersey().register(publisher);

       // new Listener().listen();
        
        // Create a queue sender
        ActiveMQSender sender = activeMQBundle.createSender("test-queue", false);

        // or like this:
        ActiveMQSender sender2 = activeMQBundle.createSender("queue:test-queue", false);

        // where messages have a 60 second time-to-live:
        ActiveMQSender sender3 = activeMQBundle.createSender("queue:test-queue", false, Optional.of(60));

        // Create a topic-sender
        ActiveMQSender sender4 = activeMQBundle.createSender("topic:test-topic", false);

        // use it
        sender.send( new Status(12, "hahahah") );
        sender.sendJson("{'a':2, 'b':3}");

        // If you require full control of message creation, pass a Java 8 function that takes a javax.jms.Session parameter:
//        sender.send((Session session) -> {
//            TextMessage message = session.createTextMessage();
//            message.setText("{'a':2, 'b':3}");
//            message.setJMSCorrelationID(myCorrelationId);
//            return message;
//        });


        // Create a receiver that consumes json-strings using Java 8
        activeMQBundle.registerReceiver(
                "test-queue", // default is queue. Prefix with 'topic:' or 'queue:' to choose
                (json) -> System.out.println("json: " + json),
                String.class,
                true);


        // Create a receiver that consumes SomeObject via Json using Java 8
//        activeMQBundle.registerReceiver(
//                            "test-queue-2",
//                            (o) -> System.out.println("Value from o: " + o.getValue()),
//                            SomeObject.class,
//                            true);
//
//        // Create a receiver that consumes SomeObject via Json using Java 7
//        activeMQBundle.registerReceiver(
//                            "test-queue-3",
//                            new ActiveMQReceiver<SomeObject>() {
//                                @Override
//                                public void receive(SomeObject o) {
//                                    System.out.println"Value from o: " + o.getValue());
//                                }
//                            },
//                            SomeObject.class,
//                            true);
//    }

    }

}
