package connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    public static final String URL = "jdbc:mysql://localhost/userdb";
    public static final String USER = "root";
    public static final String PASS = "1111";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
