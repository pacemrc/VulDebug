package dubbo.util;

import java.io.IOException;

public class ExecTest {
    public ExecTest() throws IOException {
        new java.io.IOException().printStackTrace();
        java.lang.Runtime.getRuntime().exec("calc");
    }
}