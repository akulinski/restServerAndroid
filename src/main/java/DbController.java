import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.sql.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class DbController {
    private MysqlDataSource dataSource;
    private java.sql.Connection conn;
    private PreparedStatement preparedStatement=null;
    private final Logger logger = Logger.getLogger(DbController.class.getName());
    private FileHandler fileHandler=null;

    DbController() {

        setUpLogger();

        dataSource = new MysqlDataSource ();

        dataSource.setUser(Keys.user);
        dataSource.setPassword(Keys.password);
        dataSource.setServerName(Keys.name);
        dataSource.setDatabaseName(Keys.databaseName);

        try {
            conn = dataSource.getConnection ();
            logger.log(Level.INFO,"Connected to DB");

        } catch (SQLException e) {

            logger.log(Level.SEVERE,"SQL exception Message: "+e.getMessage()+" SQLState  "+e.getSQLState()+" Error code: "+e.getErrorCode());
            e.printStackTrace();
        }

    }

    private void setUpLogger()
    {
        try {
            fileHandler=new FileHandler("DB.log");
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();

            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    boolean addStalker(Stalker stalker)
    {
        String query="INSERT INTO STALKER VALUES(?,?,?,?,?,?)";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date=LocalDateTime.now ().toString ();
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

            logger.log(Level.INFO,"Added user to db "+stalker.toString());
            return true;
        } catch (SQLException e) {

            logger.log(Level.SEVERE,"SQL exception Message: "+e.getMessage()+" SQLState  "+e.getSQLState()+" Error code: "+e.getErrorCode());
            e.printStackTrace();
        }catch (ParseException e) {

            logger.log(Level.SEVERE,"Parse exception Message: "+e.getMessage());
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

    String updateParams(Cordinates params,String nameSTALKER,String nameVictim)
    {
        String getid="SELECT idSTALKER FROM STALKER WHERE userName=?;";



        String query="UPDATE VICTIM SET cordinatesx=?, cordinatesy=? WHERE STALKER_idSTALKER=? AND nameVICTIM=?;";
        Integer idStalker = null;

        try{

            preparedStatement=conn.prepareStatement(getid);
            preparedStatement.setString(1,nameSTALKER);
            ResultSet set=preparedStatement.executeQuery();
            while(set.next()){
                idStalker=set.getInt(1);
            }

            System.out.println(idStalker);

        }catch (SQLException e){
            e.printStackTrace ();
        }

        try
        {
            System.out.println("try");
            preparedStatement=conn.prepareStatement(query);
            System.out.println(params);
            System.out.println(Double.parseDouble(params.cordinatesx));
            System.out.println(Double.parseDouble(params.cordinatesy));
            preparedStatement.setDouble(1,Double.parseDouble(params.cordinatesx));
            preparedStatement.setDouble(2,Double.parseDouble(params.cordinatesy));
            preparedStatement.setInt(3,idStalker.intValue());
            preparedStatement.setString(4,nameVictim);
            preparedStatement.executeUpdate();

            logger.log(Level.INFO,String.format("Updated id:%s params:%s ",idStalker,params));
            return "SUCCESS";
        } catch (SQLException e) {

            logger.log(Level.SEVERE,"SQL exception Message: "+e.getMessage()+" SQLState  "+e.getSQLState()+" Error code: "+e.getErrorCode());
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


            logger.log(Level.INFO,"Added user to db "+victim.toString());
            return true;

        } catch (SQLException e) {

            logger.log(Level.SEVERE,"SQL exception Message: "+e.getMessage()+" SQLState  "+e.getSQLState()+" Error code: "+e.getErrorCode());
            e.printStackTrace();
        } catch (ParseException e) {

            logger.log(Level.SEVERE,"Parse exception Message: "+e.getMessage());
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

    String getLinks(String name){

        String query="SELECT * FROM PHOTO WHERE StalkerName=?;";

        try {
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,name);
            logger.log(Level.INFO,preparedStatement.toString());
            ResultSet set=preparedStatement.executeQuery();

            String ret="";

            while (set.next()){
                ret+=set.getString(3);
                ret+="^";
            }
            logger.log(Level.INFO, ret);

            return ret;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO,"somting went wrong");
        return  "";

    }

    Cordinates getCords(String name){

        String query="SELECT cordinatesx,cordinatesy FROM VICTIM WHERE nameVICTIM=?";

        try {
            preparedStatement=conn.prepareStatement(query);

            preparedStatement.setString(1,name);


            logger.log(Level.INFO,preparedStatement.toString());
            ResultSet set=preparedStatement.executeQuery();



            while (set.next())
            {
                String cordinatesx=set.getString(1);
                String cordinatesy=set.getString(2);

                logger.log(Level.INFO,cordinatesx+" "+cordinatesy);
                Cordinates cordinates=new Cordinates(cordinatesx,cordinatesy);
                logger.log(Level.INFO,"tomek to zyd "+cordinates.toString());
                return cordinates;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO,"returning null");
        return null;
    }

    String addLink(String stalker,String link){

        String query="INSERT INTO PHOTO VALUES(null,?,?);";

        try {
            preparedStatement=conn.prepareStatement(query);

            preparedStatement.setString(1,stalker);
            preparedStatement.setString(2,link);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "";
    }

}
