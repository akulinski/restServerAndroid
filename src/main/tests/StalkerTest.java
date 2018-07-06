import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StalkerTest {

    Stalker stalker;

    @Before
    public void setUp() throws Exception {
        stalker=new Stalker("albert",1,"pa","email","17-06-2018","17-06-2018");
    }

    @Test
    public void getPassword() {

        assertEquals("password", stalker.getPassword());
    }

    @Test (expected = IllegalStateException.class)
    public void getPasswordWithException() {

        assertEquals("password",stalker.getPassword());
    }

    @Test
    public void getEmail() {

        assertEquals("email",stalker.getEmail());
    }

    @Test
    public void getDataOfJoining() {

        assertEquals("17-06-2018",stalker.getDataOfJoining());
    }

    @Test
    public void getLastOnline() {

        assertEquals("17-06-2018",stalker.getLastOnline());
    }

    @Test
    public void getName() {

        assertEquals("albert",stalker.getName());
    }
}