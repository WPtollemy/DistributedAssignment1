package res;
import java.util.ArrayList;
import net.jini.core.entry.*;

public class WPUser implements Entry
{
    public String name, pass;
    public ArrayList<String> subscribedTopics = new ArrayList<String>();

    public WPUser()
    {
    }

    public WPUser(String name, String pass)
    {
        this.name = name;
        this.pass = pass;
    }

    public void subscribe(String topic)
    {
        subscribedTopics.add(topic);
    }
}
