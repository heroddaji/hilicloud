/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.in.mem.web.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;


public class FrameworkStatus {
    private long id;    
    
    private String content;
    
    private int activeVms = 0;
    
    public FrameworkStatus(){}

    public FrameworkStatus(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @JsonProperty
    public int getActiveVms() {
        return activeVms;
    }

    public void increaseActiveVms() {
        this.activeVms++;
    }
    
    
    
}
