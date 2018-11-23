package res;
import net.jini.core.entry.*;

public class WPUser implements Entry
{
    public String name, pass;

    public WPUser()
    {
    }

    public WPUser(String name, String pass)
    {
        this.name = name;
        this.pass = pass;
    }
}
