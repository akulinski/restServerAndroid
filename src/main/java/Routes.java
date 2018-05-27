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

        return new Gson().toJson("SUCCESS");
    };
}
