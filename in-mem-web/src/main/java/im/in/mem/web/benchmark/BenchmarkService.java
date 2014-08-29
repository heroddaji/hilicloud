/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.in.mem.web.benchmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BenchmarkService {
    
    public String testCPU() throws IOException, InterruptedException{
        
        List<String>commands = new ArrayList<String>();
        commands.add("sysbench");
        commands.add("--test=cpu");
        commands.add("--cpu-max-prime=10000");
        commands.add("run");
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        
        StringBuilder strb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line=null, previous = null;
        while((line=br.readLine()) != null){
            strb.append(line).append("\n");
        }
        
        System.out.println(strb);
        if(p.waitFor() == 0){
            System.out.println("success");
        }
        return strb.toString();
    }
    
}
