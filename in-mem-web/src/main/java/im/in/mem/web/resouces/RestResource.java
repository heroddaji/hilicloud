/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.in.mem.web.resouces;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import im.in.mem.web.core.Status;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
public class RestResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter ;

    public RestResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;        
        this.counter = new AtomicLong();
    }
    
    @GET
    @Timed
    public Status sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Status(counter.incrementAndGet(), value);
    }
}
