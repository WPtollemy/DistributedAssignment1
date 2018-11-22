package Controller;
import res.WPTopic;

public class TopicCreator
{
    private SpaceController spaceController = new SpaceController();

    public TopicCreator()
    {
    }

    public void createTopic(String topicTitle, String topicOwner)
    {
        WPTopic testing = new WPTopic(topicTitle, topicOwner);
        testing         = testing;
        spaceController.writeTopic(testing);
    }
}
