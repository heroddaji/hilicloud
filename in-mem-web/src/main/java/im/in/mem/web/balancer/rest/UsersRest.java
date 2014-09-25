/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.balancer.rest;

import com.mongodb.MongoClient;
import im.in.mem.web.dao.User;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersRest {

    Morphia morphia;
    Datastore ds;
    MongoClient mongoClient;
    String dbName = "hilicloud";
    
    public UsersRest(){
        
        try {
            mongoClient = new MongoClient();
            morphia = new Morphia();        
            ds = morphia.createDatastore(mongoClient, dbName);
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsersRest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GET    
    public List<User> getAllUsers() {
        Query q = ds.find(User.class);
        
        return q.asList();
    }

}
