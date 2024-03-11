package dubbo.util;

import com.caucho.hessian.io.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {

    public static byte[] serialize(Object obj) throws Exception{
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(arr)){
            output.writeObject(obj);
        }
        return arr.toByteArray();
    }

    public static Object unserialize(byte[] arr) throws Exception{
        Object obj;
        try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(arr))){
            obj = input.readObject();
        }
        return obj;
    }

    public static void test(Object obj) throws Exception{
        unserialize(serialize(obj));
    }

    public static byte[] hessianSerialize(Object o) throws Exception {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        HessianOutput output = new HessianOutput(bao);
        output.getSerializerFactory().setAllowNonSerializable(true);
        output.writeObject(o);
        return bao.toByteArray();
    }

    public static Object hessianUnserialize(byte[] data) throws Exception{
        HessianInput input = new HessianInput(new ByteArrayInputStream(data));
        Object obj = input.readObject();
        return obj;
    }

    public static byte[] hessian2Serialize(Object o) throws Exception {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(bao);
        output.setSerializerFactory(new SerializerFactory());
        output.getSerializerFactory().setAllowNonSerializable(true);
        output.writeObject(o);
        output.flush();
        return bao.toByteArray();
    }

    public static Object hessian2Unserialize(byte[] data) throws Exception{
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        Object obj = input.readObject();
        return obj;
    }


    public static void hessianTest(Object obj) throws Exception{
        hessianUnserialize(hessianSerialize(obj));
    }

    public static void hessian2Test(Object obj) throws Exception{
        hessian2Unserialize(hessian2Serialize(obj));
    }
}