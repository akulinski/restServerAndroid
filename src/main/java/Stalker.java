import java.util.Date;

public class Stalker extends User {
    private String password;
    private String email;
    String dataOfJoining;
    String lastOnline;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDataOfJoining() {
        return dataOfJoining;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public Stalker(String username, int id, String password, String email, String dataOfJoining, String lastOnline) {

        super(username,id);
        this.password = password;
        this.email = email;
        this.dataOfJoining = dataOfJoining;
        this.lastOnline = lastOnline;
    }

    @Override
    public String toString() {
        return password+" "+email;
    }
}
