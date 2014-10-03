/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.balancer.rest;

import com.google.common.base.Optional;
import com.mongodb.MongoClient;
import im.in.mem.web.core.dao.UserDAO;
import im.in.mem.web.core.entity.User;

import im.in.mem.web.core.view.UserView;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("users")
@Produces(MediaType.TEXT_HTML)
public class UsersRest {

    Morphia morphia;
    Datastore ds;
    MongoClient mongoClient;
    String dbName = "hilicloud";

    private final Logger log = LoggerFactory.getLogger(getClass());

    public UsersRest() {

        try {
            mongoClient = new MongoClient();
            morphia = new Morphia();
            ds = morphia.createDatastore(mongoClient, dbName);
        } catch (UnknownHostException ex) {
            log.error(ex.getMessage());
        }
    }

    @GET
    public List<User> getAllUsers() {
        Query q = ds.find(User.class);
        return q.asList();
    }

    @GET
    @Path("/{username}")
    public UserView getUserView(@PathParam("username") String username) {

        UserDAO userDao = new UserDAO(User.class, mongoClient, morphia, dbName);
        User user = userDao.findOne("username", username);
        return new UserView(user);

    }

    @GET
    @Path("/json/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserJson(@PathParam("username") String username) {

        UserDAO userDao = new UserDAO(User.class, mongoClient, morphia, dbName);
        User user = userDao.findOne("username", username);
        return user;

    }

    @GET
    @Path("all")
    public UserView fetch() {
        List<User> users = new LinkedList<>();
        users.add(new User().setUsername("user1"));
        users.add(new User().setUsername("user2"));

        return new UserView(users);
    }

}
