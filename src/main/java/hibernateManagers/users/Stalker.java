package hibernateManagers.users;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "STALKER")
public class Stalker {

    @Id
    @GeneratedValue
    @Column(name = "idSTALKER")
    private int id;

    @Expose
    @Column(name = "userName")
    private String username;

    @Expose
    @Column(name = "userPassword")
    private String password;

    @Expose
    @Column(name = "userEmail")
    private String email;

    @Expose
    @Column(name = "dateOfJoining")
    private Date dataOfJoining;

    @Expose
    @Column(name = "lastOnline")
    private Date lastOnline;

    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "stalker")
    private Set<Victim> victims;


    public Stalker() {

    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Date getDataOfJoining() {
        return dataOfJoining;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataOfJoining(Date dataOfJoining) {
        this.dataOfJoining = dataOfJoining;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Stalker(String username,String password, String email, Date dataOfJoining, Date lastOnline) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.dataOfJoining = dataOfJoining;
        this.lastOnline = lastOnline;
    }

    @Override
    public String toString() {
        return password + " " + email;
    }
}
