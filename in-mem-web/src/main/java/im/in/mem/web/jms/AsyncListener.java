/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * }
 *
 * @author dai
 */
public class AsyncListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(AsyncListener.class);

    @Override
    public void onMessage(Message message) {
        
        
        
        if (message instanceof TextMessage) {
            try {
                logger.info("haha {} ", ((TextMessage) message).getText());
            } catch (JMSException e) {
                logger.error("Can't extract text from received message", e);
            }
        } else {
            logger.info("Unexpected non-text message received.");
        }

    }
}
