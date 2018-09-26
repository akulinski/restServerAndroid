import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used for storing sessions
 * of users
 */
public class SingletonLoginController {

    private static SingletonLoginController instance=null;

    /**
     * Map of sessions sessionKey-user
     */
    private Map<String,String> mapOfSessions;


    private SingletonLoginController(){

        mapOfSessions=new ConcurrentHashMap<>();
    }


    public synchronized static SingletonLoginController getInstance(){

        if(instance==null){
            instance=new SingletonLoginController();
        }
        return instance;
    }

    /**
     *
     * @param username Name of user
     * @param sesionID Id of session
     */
    public void addSession(String username,String sesionID){

        mapOfSessions.put(sesionID,username);
    }

    /**
     *
     * @param val Id of sessions
     * @return boolean that indicates whether
     * session is valid
     */
    public boolean check(String val){
        if(mapOfSessions.get(val)!=null){
            return true;
        }
        return false;
    }

}
