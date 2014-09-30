package im.in.mem.web.core.dao;

import com.mongodb.MongoClient;
import im.in.mem.web.core.entity.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;


public class UserDAO extends BasicDAO<User, ObjectId>{

    public UserDAO(Class<User> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

}
