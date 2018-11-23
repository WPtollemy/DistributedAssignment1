package res;
import net.jini.core.entry.*;

public class WPMessage implements Entry
{
    public String topic, messageOwner, message;

    public WPMessage()
    {
    }

    public WPMessage(String topic, String messageOwner, String message)
    {
        this.topic        = topic;
        this.messageOwner = messageOwner;
        this.message      = message;
    }
}
