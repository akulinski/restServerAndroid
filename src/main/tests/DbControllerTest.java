import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class DbControllerTest {


    DbController dbController;

    @Before
    public void setUp() throws Exception {
        dbController = new DbController();
    }

    @org.junit.Test
    public void addStalker() {
        Stalker stalker=new Stalker(RandomStringGenerator.getInstance().getString(5),1,"password","email","17-06-2018","17-06-2018");
        assertTrue(dbController.addStalker(stalker));
    }

    @org.junit.Test
    public void getStalker() {

        assertEquals("AZF12 email",dbController.getStalker("AZF12","password"));
    }

    @org.junit.Test
    public void updateParams() {

        Cordinates cordinates=new Cordinates("44.3222","32.123321");

        assertEquals("SUCCESS",dbController.updateParams(cordinates,"tomeczek","albi"));

    }

    @org.junit.Ignore
    @org.junit.Test
    public void addVictim() {

        Cordinates cordinates=new Cordinates("44.3222","32.123321");

        Victim victim=new Victim(RandomStringGenerator.getInstance().getString(5),1,21,"17-06-2018","17-06-2018","aaa");

        assertTrue(dbController.addVictim(victim));

    }

    @org.junit.Test
    public void getVictim() {
    }

    @org.junit.Test
    public void getLinks() {
    }

    @org.junit.Test
    public void getCords() {
    }

    @org.junit.Test
    public void addLink() {
    }

}