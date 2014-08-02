/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.server.rest;

import im.framework.api.client.jms.PublisherBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.JMSException;

@Stateless
public class ServiceImpEJB implements ServiceIntRest {

    static int count = 0;

    public ServiceImpEJB() {
        count++;
        System.out.println("request count:" + count);
    }

    @Override
    public int getNumberOfNodes() {
        return 1;
    }

    @Override
    public int registerToMasterController() {
        try {
            new PublisherBean().send();
            return 1;
        } catch (JMSException ex) {
            Logger.getLogger(ServiceImpEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 1;
    }
    

}
