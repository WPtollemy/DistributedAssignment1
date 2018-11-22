package res;
import net.jini.core.entry.*;

public class WPTopic implements Entry
{
	public String title, topicOwner;

	public WPTopic()
	{
    }

	public WPTopic(String title, String topicOwner)
	{
		this.title      = title;
		this.topicOwner = topicOwner;
	}
}
