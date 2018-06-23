import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonLoginController {

    private static SingletonLoginController instance=null;

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

    public void addSession(String username,String sesionID){

        mapOfSessions.put(sesionID,username);
    }

    public boolean check(String val){
        if(mapOfSessions.get(val)!=null){
            return true;
        }
        return false;
    }

}
