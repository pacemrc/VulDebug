package utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Util {
    private static final String BASE64_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public Base64Util() {
    }

    public static String decode(String data, String charset) throws UnsupportedEncodingException {
        return decode(data.getBytes(charset), charset);
    }

    public static String decode(byte[] bytes, String charset) throws UnsupportedEncodingException {
        String result = null;
        byte[] _b = decodeByte(bytes, charset);
        if (_b != null) {
            result = new String(_b, charset);
        }

        return result;
    }

    public static byte[] decodeByte(byte[] bytes, String charset) throws UnsupportedEncodingException {
        int length = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] result = null;
        byte[] bt = new byte[3];
        int flag = -1;

        for(int i = 0; i < length; i += 4) {
            int j;
            for(j = i; j < i + 4 && j < length; ++j) {
                byte b = (byte)"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(bytes[j]);
                if (64 != b) {
                    switch(j % 4) {
                        case 0:
                            bt[0] = (byte)(b << 2);
                            break;
                        case 1:
                            bt[0] = (byte)(bt[0] + ((b & 240) >> 4));
                            bt[1] = (byte)((b & 15) << 4);
                            flag = 0;
                            break;
                        case 2:
                            bt[1] = (byte)(bt[1] + ((b & 252) >> 2));
                            bt[2] = (byte)((b & 3) << 6);
                            flag = 1;
                            break;
                        case 3:
                            bt[2] += b;
                            flag = 2;
                    }
                }
            }

            for(j = 0; j <= flag; ++j) {
                out.write(bt[j]);
            }

            flag = -1;
        }

        if (out != null) {
            result = out.toByteArray();

            try {
                out.close();
            } catch (IOException var10) {
            }
        }

        return result;
    }

    public static byte[] decodeByte(byte[] bytes) throws UnsupportedEncodingException {
        int length = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] result = null;
        byte[] bt = new byte[3];
        int flag = -1;

        for(int i = 0; i < length; i += 4) {
            int j;
            for(j = i; j < i + 4 && j < length; ++j) {
                byte b = (byte)"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(bytes[j]);
                if (64 != b) {
                    switch(j % 4) {
                        case 0:
                            bt[0] = (byte)(b << 2);
                            break;
                        case 1:
                            bt[0] = (byte)(bt[0] + ((b & 240) >> 4));
                            bt[1] = (byte)((b & 15) << 4);
                            flag = 0;
                            break;
                        case 2:
                            bt[1] = (byte)(bt[1] + ((b & 252) >> 2));
                            bt[2] = (byte)((b & 3) << 6);
                            flag = 1;
                            break;
                        case 3:
                            bt[2] += b;
                            flag = 2;
                    }
                }
            }

            for(j = 0; j <= flag; ++j) {
                out.write(bt[j]);
            }

            flag = -1;
        }

        if (out != null) {
            result = out.toByteArray();

            try {
                out.close();
            } catch (IOException var9) {
            }
        }

        return result;
    }

    public static byte[] decodeByte(String data, String charset) throws UnsupportedEncodingException {
        return decodeByte(data.getBytes(charset), charset);
    }

    public static String decode(byte[] bytes, String charset, String base64) throws UnsupportedEncodingException {
        int length = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String result = null;
        byte[] bt = new byte[3];
        int flag = -1;
        base64 = base64 == null ? "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=" : base64;

        for(int i = 0; i < length; i += 4) {
            int j;
            for(j = i; j < i + 4 && j < length; ++j) {
                byte b = (byte)base64.indexOf(bytes[j]);
                if (64 != b) {
                    switch(j % 4) {
                        case 0:
                            bt[0] = (byte)(b << 2);
                            break;
                        case 1:
                            bt[0] = (byte)(bt[0] + ((b & 240) >> 4));
                            bt[1] = (byte)((b & 15) << 4);
                            flag = 0;
                            break;
                        case 2:
                            bt[1] = (byte)(bt[1] + ((b & 252) >> 2));
                            bt[2] = (byte)((b & 3) << 6);
                            flag = 1;
                            break;
                        case 3:
                            bt[2] += b;
                            flag = 2;
                    }
                }
            }

            for(j = 0; j <= flag; ++j) {
                out.write(bt[j]);
            }

            flag = -1;
        }

        if (out != null) {
            result = new String(out.toByteArray(), charset);

            try {
                out.close();
            } catch (IOException var11) {
            }
        }

        return result;
    }

    public static String encode(byte[] bValue, String encode, String strTableBase64) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        if (strTableBase64 == null || "".equalsIgnoreCase(strTableBase64)) {
            strTableBase64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        }

        byte[] d = new byte[4];

        try {
            int count = 0;
            byte[] x = bValue;

            while(count < x.length) {
                byte c = x[count];
                ++count;
                d[0] = (byte)((c & 252) >>> 2);
                d[1] = (byte)((c & 3) << 4);
                if (count < x.length) {
                    c = x[count];
                    ++count;
                    d[1] += (byte)((c & 240) >>> 4);
                    d[2] = (byte)((c & 15) << 2);
                    if (count < x.length) {
                        c = x[count];
                        ++count;
                        d[2] = (byte)(d[2] + ((c & 192) >>> 6));
                        d[3] = (byte)(c & 63);
                    } else {
                        d[3] = 64;
                    }
                } else {
                    d[2] = 64;
                    d[3] = 64;
                }

                for(int n = 0; n <= 3; ++n) {
                    o.write(strTableBase64.charAt(d[n]));
                }
            }
        } catch (StringIndexOutOfBoundsException var11) {
        }

        String temp = null;
        if (o != null) {
            try {
                temp = new String(o.toByteArray(), encode);
            } catch (UnsupportedEncodingException var10) {
            }

            try {
                o.close();
            } catch (IOException var9) {
                o = null;
            }
        }

        return temp;
    }

    public static String decodeString(String strValue, String encode, String strTableBase64) throws UnsupportedEncodingException {
        return new String(decode(strValue, encode, strTableBase64), encode);
    }

    public static byte[] decode(String strValue, String encode, String strTableBase64) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        byte[] d = new byte[4];
        if (strTableBase64 == null || "".equalsIgnoreCase(strTableBase64)) {
            strTableBase64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        }

        try {
            int count = 0;
            byte[] x = null;

            try {
                x = strValue.getBytes(encode);
            } catch (UnsupportedEncodingException var10) {
            }

            while(count < x.length) {
                for(int n = 0; n <= 3; ++n) {
                    if (count >= x.length) {
                        d[n] = 64;
                    } else {
                        int y = strTableBase64.indexOf(x[count]);
                        if (y < 0) {
                            y = 65;
                        }

                        d[n] = (byte)y;
                    }

                    ++count;
                }

                o.write((byte)(((d[0] & 63) << 2) + ((d[1] & 48) >> 4)));
                if (d[2] != 64) {
                    o.write((byte)(((d[1] & 15) << 4) + ((d[2] & 60) >> 2)));
                    if (d[3] != 64) {
                        o.write((byte)(((d[2] & 3) << 6) + (d[3] & 63)));
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException var11) {
        }

        if (o != null) {
            d = o.toByteArray();

            try {
                o.close();
                o = null;
            } catch (IOException var9) {
            }
        }

        return d;
    }

    public static void main(String[] arg) {
        String s = encode("我是中国人".getBytes(), "ISO-8859-1", "");
        System.out.println(s);

        try {
            FileOutputStream out = new FileOutputStream("c:/base_java.txt");
            out.write(s.getBytes("ISO-8859-1"));
            out.flush();
        } catch (Exception var3) {
        }

    }
}
