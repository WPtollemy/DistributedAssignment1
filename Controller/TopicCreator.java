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
        WPTopic topicToAdd = new WPTopic(topicTitle, Main.getLoggedUser().name);
        WPTopic topicToCheck = new WPTopic(topicTitle, null);

        if (null != spaceController.readTopic(topicToCheck)) {
            //"fail" for already existing topic
            return false;
        }

        //presume that topic gets created (should validate)
        spaceController.writeTopic(topicToAdd);
        return true;
    }
}
