import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.*;

import java.io.IOException;
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
        logger.log (Level.INFO, "test by: " + request.ip () + " " + request.headers ());
        System.out.println ("working test");
        String jsonString = "{\"Success\":\"true\"}";
        JsonObject jsonObject = (JsonObject) new JsonParser ().parse(jsonString);
        return jsonObject;

    };

    public Route addStalker=(Request req,Response res)->
    {
        res.type("application/json");

        Stalker stalker=new Gson().fromJson(req.body(),Stalker.class);

        System.out.println(stalker);
        dbController.addStalker(stalker);

        logger.log (Level.INFO, "addStalker by: " + req.ip () + " " + req.headers ());
        return new Gson().toJson("SUCCESS");
    };

    public Route getStalker=(Request req,Response res)->
    {

        System.out.println("test");
        res.type("application/json");

        logger.log (Level.INFO, "getStalker by: " + req.ip () + " " + req.headers ());
        return new Gson().toJson(dbController.getStalker(req.params(":name"),req.params(":password")));
    };

    public Route updateParams=(Request req,Response res)->
    {
        Integer id=Integer.parseInt(req.params(":id"));
        System.out.println("update params");

        String params=new Gson().fromJson(req.body(),String.class);
        System.out.println("update params");
        dbController.updateParams(id,params);

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
}
