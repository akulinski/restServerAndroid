package core.users;


import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "PHOTO")
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "LINK")
    private String link;

    @ManyToOne
    @JoinColumn(name = "idVictim", nullable = false)
    private Victim victim;


    public Photo(String link,Victim victim) {
        this.link = link;

        this.victim = victim;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", victim=" + victim +
                '}';
    }

    public Photo() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
