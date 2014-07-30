/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.framework.api.server.rest;

import javax.ejb.Stateless;

@Stateless
public class ServiceImpEJB implements ServiceIntRest {

    static int count = 0;

    public ServiceImpEJB() {
        count++;
        System.out.println("request count:" + count);
    }

    @Override
    public int getNumberOfNodes() {
        return 99;
    }

}
