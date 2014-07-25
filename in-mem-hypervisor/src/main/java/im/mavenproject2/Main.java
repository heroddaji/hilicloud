/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.mavenproject2;

import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.libvirt.Connect;
import org.libvirt.ConnectAuth;
import org.libvirt.ConnectAuthDefault;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.libvirt.NodeInfo;

/**
 *
 * @author dai
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("hello");
        //test1();
        String[] files = {""};
        test2(files);
    }

    public static void test2(String [] rgstring) {

        try {
            // Create the initial context.  The environment
            // information specifies the JNDI provider to use
            // and the initial URL to use (in our case, a
            // directory in URL form -- file:///...).
            Hashtable hashtableEnvironment = new Hashtable();
            hashtableEnvironment.put(
                    Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.fscontext.RefFSContextFactory"
            );
            hashtableEnvironment.put(
                    Context.PROVIDER_URL,
                    rgstring[0]
            );
            Context context = new InitialContext(hashtableEnvironment);
            // If you provide no other command line arguments,
            // list all of the names in the specified context and
            // the objects they are bound to.
            if (rgstring.length == 1) {
                NamingEnumeration namingenumeration = context.listBindings("");
                while (namingenumeration.hasMore()) {
                    Binding binding = (Binding) namingenumeration.next();
                    System.out.println(
                            binding.getName() + " "
                            + binding.getObject()
                    );
                }
            } // Otherwise, list the names and bindings for the
            // specified arguments.
            else {
                for (int i = 1; i < rgstring.length; i++) {
                    Object object = context.lookup(rgstring[i]);
                    System.out.println(
                            rgstring[i] + " "
                            + object
                    );
                }
            }
            context.close();
        } catch (NamingException namingexception) {
            namingexception.printStackTrace();
        }
    }

    public static void test1() {

        try {
            ConnectAuth ca = new ConnectAuthDefault();
            Connect conn = new Connect("qemu:///system", ca, 0);
            NodeInfo ni = conn.nodeInfo();
            System.out.println("model: " + ni.model + " mem(kb):" + ni.memory);

            int numOfVMs = conn.numOfDomains();

            for (int i = 1; i < numOfVMs + 1; i++) {
                Domain vm = conn.domainLookupByID(i);
                System.out.println("vm name: " + vm.getName() + "  type: " + vm.getOSType()
                        + " max mem: " + vm.getMaxMemory() + " max cpu: " + vm.getMaxVcpus());
            }

            String haha = conn.getCapabilities();
            System.out.println("get capability:" + haha);

        } catch (LibvirtException ex) {
            System.err.println(ex);
        }
    }
}
