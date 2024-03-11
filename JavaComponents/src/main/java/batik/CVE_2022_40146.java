package batik;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CVE_2022_40146 {

    public static void main(String[] args) throws IOException {

        JPEGTranscoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_EXECUTE_ONLOAD, Boolean.TRUE);
        t.addTranscodingHint(JPEGTranscoder.KEY_ALLOWED_SCRIPT_TYPES,"application/java-archive");
        FileInputStream stream = new FileInputStream("/path/to/cve_2022_40146_win.svg");
        TranscoderInput input = new TranscoderInput(stream);
        FileOutputStream fos = new FileOutputStream("out.jpg");
        TranscoderOutput output = new TranscoderOutput(fos);
        try {
            t.transcode(input,output);
        } catch (TranscoderException e) {
            e.printStackTrace();
        }
        fos.close();
    }
}
