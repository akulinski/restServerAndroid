import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DbController {

    private java.sql.Connection conn;
    private Statement stmt = null;
    private PreparedStatement preparedStatement=null;
    MysqlDataSource dataSource;

    DbController()
    {
        dataSource.setUser(Keys.user);
        dataSource.setPassword(Keys.password);
        dataSource.setDatabaseName(Keys.databaseName);
        dataSource.setServerName("35.193.226.100");

        try {
            conn=dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
