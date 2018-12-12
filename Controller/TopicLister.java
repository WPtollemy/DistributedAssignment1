package Controller;
import java.util.ArrayList;
import View.TopicCreator;
import View.TopicViewer;
import res.WPTopic;

public class TopicLister
{
    //JavaSpace
    private SpaceController spaceController = new SpaceController();

    public TopicLister()
    {
    }

    public void createTopic()
    {
        TopicCreator topicCreator = TopicCreator.getInstance();
        topicCreator.setVisible(true);
    }

    public void viewTopic(String selectedTopic)
    {
        TopicViewer topicViewer = new TopicViewer(selectedTopic);
    }

    public ArrayList<String> getTopicList(boolean userTopicsOnly)
    {
        ArrayList<String> topicsTitles = new ArrayList<String>();

        for (WPTopic topic : spaceController.getTopicList()) {
            if (!userTopicsOnly ||
                    topic.topicOwner.equals(Main.getLoggedUser().name))
                topicsTitles.add(topic.title);
        }

        return topicsTitles;
    }

    public void deleteTopic(String topicTitle)
    {
        WPTopic template = new WPTopic();
        template.title = topicTitle;

        spaceController.deleteTopic(template);
    }
}
