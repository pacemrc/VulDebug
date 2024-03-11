import utils.CoreUtil;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static utils.CoreUtil.decryptPatchFile;
import static utils.CoreUtil.getRMIdecode;

public class test {

    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        String plaintext = "UserService checkVersion [\"2023-03-31 18:56:53\"]";
        String rmIencode = CoreUtil.getRMIencode(plaintext);
        System.out.println(rmIencode);

        String data = "zDp4Wp4gRip iIpiGZp4DRw6 /JV/uuu7uNf7NfN1/u71'/NOJM/NOJN/uu/JT";
        getRMIdecode(data);
//        decryptPatchFile("D:\\tmp\\patch.patches","D:\\tmp\\patch.zip");

    }
}