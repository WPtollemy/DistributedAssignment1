package tests.Controller;
import Controller.Login;
import Controller.Main;
import Controller.SpaceController;
import View.Register;
import junit.framework.*;
import org.junit.Test;
import res.*;

public class LoginControllerTest extends TestCase
{
    private Login loginController;
    private SpaceController spaceController = new SpaceController();

    protected void setUp()
    {
        loginController = new Login();
    }

    @Test
    public void testIsValidUserWhenFoundInSpace()
    {
        WPUser user = new WPUser("user", "pass");
        spaceController.writeUser(user);

        assertTrue(loginController.validUser("user", "pass"));
    }

    @Test
    public void testIsNotValidUserWhenFoundInSpace()
    {
        assertFalse(loginController.validUser("invalidTestName", "invalidTestPassword"));
    }

    @Test
    public void testRegisterLoadsRegisterForm()
    {
        loginController.registerUser();

        Register register = Register.getInstance();
        assertTrue(register.isShowing());
    }

    @Test
    public void testInvalidUserDoesntLogIn()
    {
        loginController.loginUser("non-existant user", "with this password");

        assertNull(Main.getLoggedUser());
    }

    @Test
    public void testValidUserGetsLoggedIn()
    {
        WPUser user = new WPUser("user", "pass");
        spaceController.writeUser(user);

        loginController.loginUser("user", "pass");

        assertNotNull(Main.getLoggedUser());

        //Ensure static logged user is reset
        Main.logUserIn(null);
    }
}
