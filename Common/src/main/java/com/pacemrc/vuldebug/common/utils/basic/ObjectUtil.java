package com.pacemrc.vuldebug.common.utils.basic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class ObjectUtil {

    public static byte[] getByte(Object obj) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
        bos.close();

        return bos.toByteArray();

    }

    public static String ObjectToBase64Str(Object obj) throws IOException {

        byte[] bytes = getByte(obj);
        String base64Str = Base64.getEncoder().encodeToString(bytes);

        return base64Str;
    }

    public static byte[] ObjectToByteArray(Object obj) throws IOException {

        byte[] bytes = getByte(obj);

        return bytes;

    }

    public static String ObjectToHexString(Object obj) throws IOException {

        byte[] bytes = getByte(obj);

        StringBuilder sb = new StringBuilder();
        for (byte bt : bytes) {
            sb.append(HEX_CHAR_TABLE[(bt & 0xf0) >> 4]);
            sb.append(HEX_CHAR_TABLE[bt & 0x0f]);
        }
        return sb.toString();
    }

    private static final char[] HEX_CHAR_TABLE = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

}
