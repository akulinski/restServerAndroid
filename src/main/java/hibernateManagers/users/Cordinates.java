package hibernateManagers.users;

import com.google.gson.annotations.Expose;

public class Cordinates {

    @Expose
    private String cordinatesx;

    @Expose
    private String cordinatesy;

    public void setCordinatesx(String cordinatesx) {
        this.cordinatesx = cordinatesx;
    }

    public void setCordinatesy(String cordinatesy) {
        this.cordinatesy = cordinatesy;
    }


    public Cordinates(String cordinatesx, String cordinatesy) {
        this.cordinatesx = cordinatesx;
        this.cordinatesy = cordinatesy;
    }

    public String getX() {
        return cordinatesx;
    }

    public String getY() {
        return cordinatesy;
    }

    @Override
    public String toString() {
        return cordinatesx + " " + cordinatesy;
    }
}
