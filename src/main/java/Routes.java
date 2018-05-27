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
}
