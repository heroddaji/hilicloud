/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.framework.api.server.servlet;

import im.framework.api.server.jms.ListenerBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class InitServletContext implements ServletContextListener{

    private static Logger logger = Logger.getLogger("InitServletContext");
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
            logger.info("ServletContext init, listern for jms events");
            new ListenerBean().listen();
        
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("destroyed");  
    }
    
}
