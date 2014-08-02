/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author dai
 */
@Path("rest")
public interface ServiceIntRest {    
   
    @GET
    @Path("info/nodes")
    public int getNumberOfNodes();    
    
    
    @GET
    @Path("client/register")
    public int registerToMasterController();    
   

    
}
