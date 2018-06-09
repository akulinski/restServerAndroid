import static spark.Spark.*;

import com.google.gson.Gson;
import spark.*;

public class Routes {
    private DbController dbController;
    Routes(DbController db)
    {
        dbController=db;

    }

    public Route testRoute =(Request request, Response response) ->
    {
        return new Gson().toJson("SUCCESS:TRUE");
    };

    public Route addStalker=(Request req,Response res)->
    {
        res.type("application/json");

        Stalker stalker=new Gson().fromJson(req.body(),Stalker.class);

        System.out.println(stalker);
        dbController.addStalker(stalker);
        return new Gson().toJson("SUCCESS");
    };

    public Route getStalker=(Request req,Response res)->
    {

        System.out.println("test");
        res.type("application/json");

        return new Gson().toJson(dbController.getStalker(req.params(":name"),req.params(":password")));
    };

    public Route updateParams=(Request req,Response res)->
    {
        Integer id=Integer.parseInt(req.params(":id"));
        System.out.println("update params");

        String params=new Gson().fromJson(req.body(),String.class);
        System.out.println("update params");
        dbController.updateParams(id,params);

        return new Gson().toJson("SUCCESS");
    };

    public Route addVictim=(Request req,Response res)->
    {
        System.out.println("WORKING1");
        Victim victim=new Gson().fromJson(req.body(),Victim.class);
        if(dbController.addVictim(victim))
            return new Gson().toJson("SUCCESS");
        else
            return new Gson().toJson("ERROR");
    };


    public Route getVictim=(Request req,Response res)->
    {
        System.out.println("getVictim");
        Integer id=Integer.parseInt(req.params(":id"));
        String name=req.params(":name");
        Victim victim=dbController.getVictim(id.intValue(),name);

        if(victim!= null)
            return new Gson().toJson(victim);
        else
            return new Gson().toJson("ERROR");

    };
}
