package Controller;
import res.WPTopic;

public class TopicCreator
{
    private SpaceController spaceController = new SpaceController();

    public TopicCreator()
    {
    }

    public boolean createTopic(String topicTitle)
    {
        WPTopic testing = new WPTopic(topicTitle, Main.getLoggedUser().name);

        if (null != spaceController.readTopic(testing)) {
            //"fail" for already existing topic
            return false;
        }

        //presume that topic gets created (should validate)
        spaceController.writeTopic(testing);
        return true;
    }
}
