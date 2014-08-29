/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.balancer;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import im.in.mem.web.WebApplication;
import im.in.mem.web.benchmark.BenchmarkService;
import im.in.mem.web.controller.FrameworkStatus;
import im.in.mem.web.jms.JmsHandler;
import im.in.mem.web.jms.JsonMessage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.activemq.command.ActiveMQTextMessage;

@Path("/mcimbs")
@Produces(MediaType.APPLICATION_JSON)
public class RestResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public RestResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public FrameworkStatus sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new FrameworkStatus(counter.incrementAndGet(), value);
    }

    @GET
    @Path("register")
    public int testJms() throws JMSException {
        JmsHandler jmsManager = WebApplication.getInstance().getJmsManager();
      //  JsonMessage jmsMes = new JsonMessage("register");
        //jmsManager.createSender().send(jmsMes);

        return 1;
    }

    @GET
    @Path("status")
    public FrameworkStatus status() {
        return WebApplication.getFrameworkStatus();
    }

    @GET
    @Path("benchmark")
    public String benchmark() {
        return "db   - test database\n"
                + "disk - test disk\n"
                + "mem  - test memory"
                + "cpu  - test cpu";
    }

    @GET
    @Path("benchmark/db")
    public String benchmarkDB() {
        return "db   - test database\n";
    }

    @GET
    @Path("benchmark/cpu")
    public String benchmarkCpu() throws IOException, InterruptedException {
        return new BenchmarkService().testCPU();
    }
    
    @GET
    @Path("benchmark/disk")
    public String benchmarkDisk()  {
        return "disk benchmark";

    }

    @GET
    @Path("benchmark/mem")
    public String benchmarkMem() {
        return "mem  - test memory\n";

    }
}