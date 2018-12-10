package Controller;
import java.util.ArrayList;
import View.TopicCreator;
import View.TopicViewer;

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
        topicViewer.setVisible(true);
    }

    public ArrayList<String> getTopicList()
    {
        return spaceController.getTopicList();
    }
}
