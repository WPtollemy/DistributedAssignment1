package Controller;
import res.WPTopic;

public class TopicCreator
{
    private SpaceController spaceController = new SpaceController();

    public TopicCreator()
    {
    }

    public void createTopic(String topicTitle)
    {
        WPTopic testing = new WPTopic(topicTitle, Main.getLoggedUser().name);
        spaceController.writeTopic(testing);
    }
}
