import java.util.Date;

public class Stalker extends User {
    private String password;
    private String emial;
    Date dataOjJoining;
    Date lastOnline;

    public Stalker(String password, String emial, Date dataOjJoining, Date lastOnline) {
        this.password = password;
        this.emial = emial;
        this.dataOjJoining = dataOjJoining;
        this.lastOnline = lastOnline;
    }
}
