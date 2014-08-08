/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.in.mem.web.jms;

import com.kjetland.dropwizard.activemq.ActiveMQReceiver;
import im.in.mem.web.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsListener implements ActiveMQReceiver<JmsMessage>{
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public void receive(JmsMessage message) {
        log.info("get message:" + message);
        
        if (message.getContent().equals("register")){
            WebApplication.getFrameworkStatus().increaseActiveVms();
        }
    }    
    
}
