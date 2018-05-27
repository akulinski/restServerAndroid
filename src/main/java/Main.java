import static spark.Spark.*;

import com.google.gson.Gson;
import spark.*;

public class Main {

    public static void main(String[] args)
    {
        DbController db=new DbController();

        Routes routes=new Routes(db);

        get("/test",routes.testRoute);

    }

}
