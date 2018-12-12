package res;
import net.jini.core.entry.*;

public class WPNotificationRequest implements Entry
{
    public String topic, user;

    public WPNotificationRequest()
    {
    }

    public WPNotificationRequest(String topic, String user)
    {
        this.topic = topic;
        this.user  = user;
    }
}
