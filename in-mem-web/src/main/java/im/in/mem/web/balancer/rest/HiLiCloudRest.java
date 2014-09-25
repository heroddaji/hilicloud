/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.in.mem.web.balancer.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import im.in.mem.web.WebApplication;
import im.in.mem.web.benchmark.BenchmarkService;
import im.in.mem.web.core.Framework;
import im.in.mem.web.jms.JmsHandler;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hilicloud")
@Produces(MediaType.APPLICATION_JSON)
public class HiLiCloudRest {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    //UsersRest userRest ;

    public HiLiCloudRest(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        
       // userRest = new UsersRest();
    }

    @GET
    @Timed
    public Framework sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Framework(counter.incrementAndGet(), value);
    }

    @GET
    @Path("test/register")
    public int testJms() throws JMSException {
        
    
        //  JsonMessage jmsMes = new JsonMessage("register");
        //jmsManager.createSender().send(jmsMes);

        return 1;
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
    
    @GET
    @Path("benchmark/test")
    public String benchmarkTest() throws IOException {
        java.nio.file.Path file = Paths.get("/media/disk6/Dropbox/dev/in-mem/in-mem-web/src/main/resources/im/in/mem/web/resources/rest.txt");
        return Files.readAllLines(file).toString();

    }
}
