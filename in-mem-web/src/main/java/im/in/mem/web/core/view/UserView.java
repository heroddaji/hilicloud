/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.core.view;


import im.in.mem.web.core.entity.User;
import io.dropwizard.views.View;
import java.util.List;



public class UserView extends View {
    
    private User user;
    private List<User> users;
    public UserView(User user){
        //super("user.mustache");
        super("user.ftl");
        this.user = user;
    }
    
     public UserView(List<User> users) {
        super("user.mustache");
        this.users = users;
    }
    
    public User getUser(){
        return user;
    }
}
