package utils;


import java.io.*;
import java.net.URLEncoder;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64InputStream;
import smartbi.framework.rmi.RMICoder;

public class CoreUtil {

    public static String getRMIencode(String plaintext) throws UnsupportedEncodingException {

        String encode = "0123456789ABCDEF!'()*-.GHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~";
        String code   = "q1yNrpn0L7(Xxs)MW8aYTgjkGh54P63A!uilDZBSzQVfEwIm~-vdcRtK9'Oe.H*C2JoF_Ub";
        StringBuilder decodestring = new StringBuilder();
        for (int j = 0; j < plaintext.length();j++){
            char charC = plaintext.charAt(j);
            if(code.indexOf(charC)!=-1){
                for (int i = 0; i < encode.length(); i++) {
                    char charA = encode.charAt(i);
                    char charB = code.charAt(i);
                    if(charC == charB){
                        decodestring.append(charA);
                    }
                }
            }else{
                decodestring.append(charC);
            }

        }

        String urlEncode = URLEncoder.encode(decodestring.toString(), "UTF-8");

        return urlEncode;
    }

    public static void getRMIdecode(String encode){

        String[] result = RMICoder.decode(encode);
        String[] var3 = result;
        int var4 = result.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String temp = var3[var5];
            System.out.println(temp);
        }
    }

    public static void decryptPatchFile(String sourcePath, String destPath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        FileInputStream fin = new FileInputStream(sourcePath);
        Base64InputStream bin = new Base64InputStream(fin);
        String mode = "AES/CBC/PKCS5Padding";
        String key = "1234567812345678";
        String iv = "1234567812345678";
        Cipher cipher = Cipher.getInstance(mode);
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("utf-8"));
        cipher.init(2, keyspec, ivspec);
        CipherInputStream cin = new CipherInputStream(bin, cipher);

        FileOutputStream fos = new FileOutputStream(destPath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = cin.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        fos.close();
        cin.close();
        bin.close();
        fin.close();
    }
}
