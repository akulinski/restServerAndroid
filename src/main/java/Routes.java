import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import spark.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;




public class Routes {
    private DbController dbController;

    private final Logger logger = Logger.getLogger(DbController.class.getName());
    private FileHandler fileHandler=null;

    Routes(DbController db)
    {
        setUpLogger();
        dbController=db;

    }

    private void setUpLogger ( )
    {
        try
        {
            fileHandler = new FileHandler ( "routes.log" );
            logger.addHandler ( fileHandler );

            SimpleFormatter formatter = new SimpleFormatter ( );

            fileHandler.setFormatter ( formatter );

        }
        catch ( IOException e )
        {
            e.printStackTrace ( );
        }
    }



    public Route testRoute = (Request request, Response response) ->
    {
      //  logger.log (Level.INFO, "test by: " + request.ip () + " " + request.headers ());
      //  System.out.println ("working test");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty ("success","true");

        return jsonObject;
    };

    public Route addStalker=(Request req,Response res)->
    {
        res.type("application/json");

        Stalker stalker=new Gson().fromJson(req.body(),Stalker.class);

        System.out.println(stalker);
        dbController.addStalker(stalker);

        logger.log (Level.INFO, "addStalker by: " + req.ip () + " " + req.headers ());
        System.out.println ("adding Stalker");
        return new Gson().toJson("{\"Success\":\"true\"}");
    };

    public Route getStalker=(Request req,Response res)->
    {

        res.type("application/json");
        logger.log(Level.INFO, "getStalker by: " + req.ip() + " " + req.headers());

        String ret = "";

        String reqCookie = req.cookie("sesionid");
        if (reqCookie != null && SingletonLoginController.getInstance().check(reqCookie)) {
            logger.log(Level.INFO, "Found cookie");
            return new Gson().toJson("Login successful");
        } else {
            ret = dbController.getStalker(req.params(":name"), req.params(":password"));
            if (!ret.equals("")) {
                String id = RandomStringGenerator.getInstance().getHashID(10);

                SingletonLoginController.getInstance().addSession(req.params(":name"), id);
                res.cookie("sesionid", id, 100000);
                logger.log(Level.INFO, "generated Cookie id : " + id);

                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("success", "true");
                return jsonObject;

            }
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", "false");

        return jsonObject;
    };

    public Route updateParams=(Request req,Response res)->
    {

        String nameStalker=req.params(":nameStalker");
        String nameVictim=req.params(":nameVictim");

        System.out.println("update params");

        Cordinates cordinates=new Gson().fromJson(req.body(),Cordinates.class);

        System.out.println(cordinates);

        Cordinates params=new Gson().fromJson(req.body(),Cordinates.class);

        System.out.println("update params");

        dbController.updateParams(params,nameStalker,nameVictim);

        System.out.println ("Updateby: " + req.ip () + " " + req.headers ()+" body: "+req.body ());
        logger.log (Level.INFO, "Updateby: " + req.ip () + " " + req.headers ()+" body: "+req.body ());
        return new Gson().toJson("SUCCESS");
    };

    public Route addVictim=(Request req,Response res)->
    {
        System.out.println("WORKING1");
        Victim victim=new Gson().fromJson(req.body(),Victim.class);
        if(dbController.addVictim(victim))
        {
            logger.log (Level.INFO, "addVictim by: " + req.ip () + " " + req.headers ());
            return new Gson ().toJson ("SUCCESS");
        }
        else
        {
            logger.log (Level.SEVERE, "addVivtim Failed by: "+ req.ip () + " " + req.headers ());
            return new Gson ().toJson ("ERROR");
        }
    };


    public Route getVictim=(Request req,Response res)->
    {
        System.out.println("getVictim");
        Integer id=Integer.parseInt(req.params(":id"));
        String name=req.params(":name");
        Victim victim=dbController.getVictim(id.intValue(),name);

        if(victim!= null)
        {
            logger.log (Level.INFO, "getVictim by: " + req.ip () + " " + req.headers ());

            return new Gson ().toJson (victim);
        }
        else
        {
            logger.log (Level.SEVERE, "getVictim Failed by: "+ req.ip () + " " + req.headers ());
            return new Gson ().toJson ("ERROR");
        }

    };

    public Route getCords=(Request req, Response res)->
    {
        String victimName=req.params(":name");
        Cordinates cords=dbController.getCords(victimName);

        logger.log(Level.INFO,cords.toString());
        return new Gson().toJson(cords.toString());

    };

    public Route getLinks=(Request req, Response res)->
    {

        String stalkerName=req.params(":stalker");

        String links=dbController.getLinks(stalkerName);
        return new Gson().toJson(links);
    };



    public Route addLink = (Request req, Response res)->
    {
        String stalkerName=req.params(":stalker");
        String link=req.params(":link");

        dbController.addLink(stalkerName,link);

        return "";
    };
}
