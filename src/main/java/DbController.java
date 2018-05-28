import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DbController {

    private java.sql.Connection conn;
    private Statement stmt = null;
    private PreparedStatement preparedStatement=null;
    MysqlDataSource dataSource;

    DbController()
    {
        dataSource=new MysqlDataSource();
        dataSource.setUser(Keys.user);
        dataSource.setPassword(Keys.password);
        dataSource.setDatabaseName(Keys.databaseName);
        dataSource.setServerName(Keys.name);

        try {
            conn=dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    boolean addStalker(Stalker stalker)
    {
        String query="INSERT INTO STALKER VALUES(?,?,?,?,?,?)";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date="01-01-2018";

        try {
            java.util.Date myDate = formatter.parse(date);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,null);

            preparedStatement.setString(2,stalker.getName());
            preparedStatement.setString(3,stalker.getPassword());
            preparedStatement.setString(4,stalker.getEmail());
            preparedStatement.setDate(5,sqlDate);
            System.out.println("working");
            preparedStatement.setDate(6,sqlDate);

            System.out.println(query);
            preparedStatement.executeUpdate();
            System.out.println("true");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
