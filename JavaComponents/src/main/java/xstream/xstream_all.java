package xstream;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class xstream_all {
    public static void main(String[] args) throws IOException {

        String xml = new String(Files.readAllBytes(Paths.get("src/main/resources/CVE_2020_26217.txt")));
        XStream xstream = new XStream();
        xstream.fromXML(xml);
    }
}
