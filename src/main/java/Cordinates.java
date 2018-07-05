public class Cordinates {

    String cordinatesx;
    String cordinatesy;

    Cordinates(String cordinatesx,String cordinatesy){
        this.cordinatesx=cordinatesx;
        this.cordinatesy=cordinatesy;
    }


    public String getX( )
    {
        return cordinatesx;
    }

    public String getY( )
    {
        return cordinatesy;
    }

    @Override
    public String toString() {
        return cordinatesx +" "+cordinatesy;
    }
}
