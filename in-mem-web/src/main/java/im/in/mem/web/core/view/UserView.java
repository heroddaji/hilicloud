/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.core.view;


import im.in.mem.web.core.entity.User;
import io.dropwizard.views.View;



public class UserView extends View {
    
    private User user;
    public UserView(User user){
        super("user.mustache");
        this.user = user;
    }
    
    public User getUser(){
        return user;
    }
}
