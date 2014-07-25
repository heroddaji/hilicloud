/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;

@MessageDriven(mappedName = "jms/frameworkTopic")
public class JmsService implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println("Message received: " + message.getBody(String.class));
        } catch (JMSException ex) {
            Logger.getLogger(JmsService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
