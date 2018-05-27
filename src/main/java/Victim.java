import java.net.Inet4Address;
import java.util.Date;

public class Victim extends User {

    int stalkerId;
    Date lastUpdate;
    Inet4Address serverIp;
    String cordinates;

    public Victim(int stalkerId, Date lastUpdate, Inet4Address serverIp, String cordinates) {
        this.stalkerId = stalkerId;
        this.lastUpdate = lastUpdate;
        this.serverIp = serverIp;
        this.cordinates = cordinates;
    }
}
