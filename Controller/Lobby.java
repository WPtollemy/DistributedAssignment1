package Controller;
import View.TopicViewer;
import View.TopicLister;

public class Lobby 
{
    public Lobby()
    {
    }

    public void showTopicList()
    {
        TopicLister topicLister = new TopicLister();
        topicLister.setVisible(true);
    }

    public void findTopic()
    {
        /*
        TopicViewer topicViewer = TopicViewer.getInstance();
        topicViewer.setVisible(true);
        /**/
    }
}
