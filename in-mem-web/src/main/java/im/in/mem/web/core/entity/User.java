package im.in.mem.web.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;



@Entity
public class User{    
    
    @Id private ObjectId id;
    private String username;
    private String role;
    private String password;

    public User() {
    }

    public User(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    
    @JsonProperty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
