package tests.res;
import org.junit.Test;
import junit.framework.*;
import res.WPMessage;

public class WPMessageTest extends TestCase
{
    private WPMessage message;

    @Test
    public void testMessageCreated()
    {
        message = new WPMessage();
        assertNotNull(message);
    }

    @Test
    public void testMessageCreatedWithParams()
    {
        message = new WPMessage("topic", "messageOwner", "message");

        assertNotNull(message);
        assertEquals("topic", message.topic);
        assertEquals("messageOwner", message.messageOwner);
        assertEquals("message", message.message);
        assertFalse(message.isPrivate);
    }
}
