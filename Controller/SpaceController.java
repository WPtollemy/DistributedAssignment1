package Controller;
import net.jini.core.lease.*;
import net.jini.space.*;
import res.*;

public class SpaceController
{
    private static int THREE_MINUTES      = 3000 * 60;
    private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds
    private JavaSpace space;

    public SpaceController()
    {
        space = SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }
    }

    public void writeTopic(WPTopic topic)
    {
        try {
            space.write( topic, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void writeUser(WPUser user)
    {
        try {
            space.write( user, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public WPTopic readTopic(WPTopic topic)
    {
        try {
            WPTopic nextJob = (WPTopic)space.readIfExists(topic,null, TWO_SECONDS);

            return nextJob;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return topic = new WPTopic();
    }

    public boolean doesUserExist(WPUser user)
    {
        try {
            WPUser existingUser = (WPUser)space.readIfExists(user, null, TWO_SECONDS);

            if (existingUser != null)
                return true;
            
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void writeMessage(WPMessage message)
    {
        try {
            space.write( message, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public WPMessage getMessage(WPMessage message)
    {
        try {
            WPMessage exMessage = (WPMessage)space.readIfExists(message,null, TWO_SECONDS);

            return exMessage;
        }  catch ( Exception e) {
            e.printStackTrace();
        }

        return message = new WPMessage();
    }
}
