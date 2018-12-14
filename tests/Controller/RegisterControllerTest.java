package tests.Controller;
import org.junit.Test;
import junit.framework.*;
import Controller.Register;
import Controller.SpaceController;
import res.*;

public class RegisterControllerTest extends TestCase
{
    private Register registerController;
    private SpaceController spaceController = new SpaceController();

    protected void setUp()
    {
        registerController = new Register();
    }

    @Test
    public void testValidUserReturnsFalseWhenAlreadyExists()
    {
        WPUser user = new WPUser("user", "pass");
        spaceController.writeUser(user);

        assertFalse(registerController.validUser("user", "pass"));
    }

    @Test
    public void testValidUserReturnsTrueWhenUserNonExistant()
    {
        assertTrue(registerController.validUser("non-existant user name", "non-existant user pass"));
    }

    @Test
    public void testValidUserIsWrittenToSpace()
    {
        registerController.registerUser("registerTest name", "registerTest pass");

        WPUser registerUser = new WPUser("registerTest name", "registerTest pass");
        assertNotNull(spaceController.readUser(registerUser));
        assertEquals("registerTest name", spaceController.readUser(registerUser).name);
        assertEquals("registerTest pass", spaceController.readUser(registerUser).pass);
    }
}
