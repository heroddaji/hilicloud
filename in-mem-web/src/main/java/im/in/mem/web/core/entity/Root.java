/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.in.mem.web.core.entity;

import im.in.mem.web.WebConfiguration;
import im.in.mem.web.core.jms.JmsHandler;


public class Root {
    
    protected JmsHandler jmsHandler;
    protected WebConfiguration config;
    
    public Root(){       
    }
    
    public void init(WebConfiguration config){
        this.config = config;        
    }
 
    public WebConfiguration getConfig() {
        return config;
    }

    public JmsHandler getJmsHandler() {
        return jmsHandler;
    }

    public void setJmsHandler(JmsHandler jmsHandler) {
        this.jmsHandler = jmsHandler;
    }
}
