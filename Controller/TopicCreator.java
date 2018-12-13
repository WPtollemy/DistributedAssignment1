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
            //return false for already existing topic
            return false;
        }

        //return true that topic gets created
        spaceController.writeTopic(testing);
        return true;
    }
}
