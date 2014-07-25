/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@Stateless
public class ServiceImpEJB implements ServiceIntRest{

    static int count = 0;
    
    
    public ServiceImpEJB() {
        count++;
        System.out.println("request count:" + count);
    }

    
    public int getNumberOfNodes() {
        return 1;
    }  
    

    @Inject    
    JMSContext jmsContext;
    
    @Resource(lookup = "jms/frameworkTopic")
    Destination topic;
       
    public int test() {
//        Context jndiContext ;
//        try {
//            jndiContext = new InitialContext();
//            ConnectionFactory connFact = (ConnectionFactory)jndiContext.lookup("jms/DaiConnectionFactory");
//            Destination dest = (Destination)jndiContext.lookup("jms/frameworkTopic");
            
            jmsContext.createProducer().send(topic,"hahaha");
            
            int a = 0;
            
//        } catch (NamingException ex) {
//            Logger.getLogger(ServiceImpEJB.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        return 1;
    }  

    @Override
    public int doSomething() {
        return 0;
    }

  


}
