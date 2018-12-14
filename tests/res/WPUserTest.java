package tests.res;
import org.junit.Test;
import junit.framework.*;
import res.WPUser;

public class WPUserTest extends TestCase
{
    private WPUser user;

    @Test
    public void testUserCreated()
    {
        user = new WPUser();
        assertNotNull(user);
    }

    @Test
    public void testUserCreatedWithParams()
    {
        user = new WPUser("name", "pass");

        assertNotNull(user);
        assertEquals("name", user.name);
        assertEquals("pass", user.pass);
    }
}
