import java.net.Inet4Address;
import java.util.Date;

public class Victim extends User {

    public int getStalkerId() {
        return stalkerId;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getCordinates() {
        return cordinates;
    }

    private int stalkerId;
    private String lastUpdate;
    private String serverIp;
    private String cordinates;

    public Victim()
    {

    }
    public Victim(String name,int id,int stalkerId, String lastUpdate, String serverIp, String cordinates) {
        super(name,id);
        this.stalkerId = stalkerId;
        this.lastUpdate = lastUpdate;
        this.serverIp = serverIp;
        this.cordinates = cordinates;
    }
}
