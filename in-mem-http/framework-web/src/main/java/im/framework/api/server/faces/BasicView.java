/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.framework.api.server.faces;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class BasicView {
    int numActiveVMs = 0;

    public int getNumActiveVMs() {
        return numActiveVMs;
    }
    
}
